package dev.victor.api.consumption.services.impl;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import dev.victor.api.consumption.repository.IConsumptionRepository;
import dev.victor.api.consumption.services.IConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class ConsumptionServiceImpl implements IConsumptionService {

    @Autowired
    IConsumptionRepository consumptionRepository;

    @Override
    public List<ElectricityConsumption> findAll() {
        return consumptionRepository.findAll();
    }

    @Override
    public Map<String, Double> getConsumptionByDate(String meterDate) {

        SortedMap<String, Double> consumptionPerHour = new TreeMap<>();
        List<ElectricityConsumption> consumptionPerDay = consumptionRepository.findByMeterDate(meterDate);

        IntStream.range(0, 24)
                .mapToObj(hour -> String.format("%02d", hour))
                .forEach(hour -> {
                    double maximumConsumption = consumptionPerDay.stream()
                            .filter(consumption -> consumption.getMeterHour().startsWith(hour))
                            .mapToDouble(ElectricityConsumption::getActiveEnergy)
                            .max()
                            .orElse(0.0);

                    double minimumConsumption = consumptionPerDay.stream()
                            .filter(consumption -> consumption.getMeterHour().startsWith(hour))
                            .mapToDouble(ElectricityConsumption::getActiveEnergy)
                            .min()
                            .orElse(0.0);

                    double dailyConsumption = maximumConsumption - minimumConsumption;

                    consumptionPerHour.put(hour + ":00:00", dailyConsumption);
                });

        return consumptionPerHour;
    }

    @Override
    public Map<String, Double> getConsumptionByMonth(String meterDate) {
        SortedMap<String, Double> consumptionPerDays = new TreeMap<>();

        LocalDate startDate = LocalDate.parse(meterDate);
        LocalDate startOfMonth = startDate.withDayOfMonth(1);
        LocalDate endOfMonth = startDate.withDayOfMonth(startDate.lengthOfMonth());

        IntStream.rangeClosed(0, (int) ChronoUnit.DAYS.between(startOfMonth, endOfMonth))
                .mapToObj(startOfMonth::plusDays)
                .forEach(date -> getConsumptionByDay(date.toString(), consumptionPerDays));

        return consumptionPerDays;
    }

    private void getConsumptionByDay(String meterDate, SortedMap<String, Double> map) {
        List<ElectricityConsumption> consumptionsPerDay = Optional.ofNullable(consumptionRepository
                .findByMeterDate(meterDate)).orElse(Collections.emptyList());

        DoubleSummaryStatistics stats = consumptionsPerDay.stream()
                .mapToDouble(ElectricityConsumption::getActiveEnergy)
                .summaryStatistics();

        Double consumptionPerDay = stats.getMax() - stats.getMin();
        map.put(meterDate, consumptionPerDay);
    }
}
