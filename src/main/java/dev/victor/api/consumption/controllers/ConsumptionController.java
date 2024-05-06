package dev.victor.api.consumption.controllers;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import dev.victor.api.consumption.entities.RequestData;
import dev.victor.api.consumption.services.IConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/daily")
    public ResponseEntity<Map<String, Double>> getDailyConsumption(@RequestBody RequestData requestData) {

        try {
            if (requestData.getPeriod().equalsIgnoreCase("daily")) {
                return ResponseEntity.ok(consumptionService.getConsumptionByDate(requestData.getDate()));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlyConsumption(@RequestBody RequestData requestData) {

        try {
            if (requestData.getPeriod().equalsIgnoreCase("monthly")) {
                return ResponseEntity.ok(consumptionService.getConsumptionByMonth(requestData.getDate()));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
