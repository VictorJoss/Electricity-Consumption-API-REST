package dev.victor.api.consumption.services.impl;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import dev.victor.api.consumption.repository.IConsumptionRepository;
import dev.victor.api.consumption.services.IConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
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
}
