package dev.victor.api.consumption.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "consumptions")
@Data
public class ElectricityConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "active_energy")
    private double activeEnergy;
    @Column(name = "meter_date")
    private Date meterDate;
    @Column(name = "meter_hour")
    private String meterHour;
    @Column(name = "meter_id")
    private String meterId;
}
