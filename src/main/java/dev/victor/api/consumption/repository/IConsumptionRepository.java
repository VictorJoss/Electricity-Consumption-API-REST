package dev.victor.api.consumption.repository;

import dev.victor.api.consumption.entities.ElectricityConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsumptionRepository extends JpaRepository<ElectricityConsumption, Long>{
}
