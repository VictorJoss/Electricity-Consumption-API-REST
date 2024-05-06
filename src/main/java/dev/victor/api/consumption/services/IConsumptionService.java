package dev.victor.api.consumption.services;

import dev.victor.api.consumption.entities.ElectricityConsumption;

import java.util.List;
import java.util.Map;

public interface IConsumptionService {

    List<ElectricityConsumption> findAll();
    Map<String, Double> getConsumptionByDate(String meterDate);
}
