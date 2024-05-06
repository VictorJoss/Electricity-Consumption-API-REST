package dev.victor.api.consumption.services.impl;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import dev.victor.api.consumption.repository.IConsumptionRepository;
import dev.victor.api.consumption.services.IConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionServiceImpl implements IConsumptionService {

    @Autowired
    IConsumptionRepository consumptionRepository;

    @Override
    public List<ElectricityConsumption> findAll() {
        return consumptionRepository.findAll();
    }
}
