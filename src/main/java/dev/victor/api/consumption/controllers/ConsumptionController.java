package dev.victor.api.consumption.controllers;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import dev.victor.api.consumption.services.IConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumption")
public class ConsumptionController {

    @Autowired
    IConsumptionService consumptionService;

    @GetMapping("/all")
    public ResponseEntity<List<ElectricityConsumption>> findAll() {
        try {
            return ResponseEntity.ok(consumptionService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
