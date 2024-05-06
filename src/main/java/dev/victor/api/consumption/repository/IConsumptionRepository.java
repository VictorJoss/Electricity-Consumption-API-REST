package dev.victor.api.consumption.repository;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsumptionRepository extends JpaRepository<ElectricityConsumption, Long>{

    @Query(value = "SELECT * FROM CONSUMPTIONS WHERE METER_DATE=:meterDate", nativeQuery = true)
    List<ElectricityConsumption> findByMeterDate(String meterDate);
}
