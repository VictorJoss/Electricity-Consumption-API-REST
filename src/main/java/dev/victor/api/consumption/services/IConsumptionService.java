package dev.victor.api.consumption.services;

import dev.victor.api.consumption.entities.ElectricityConsumption;

import java.util.List;

public interface IConsumptionService {

    List<ElectricityConsumption> findAll();
}
