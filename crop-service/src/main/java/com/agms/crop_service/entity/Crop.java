package com.agms.crop_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String variety;
    private LocalDate plantedDate;
    private Long zoneId; // Link to the Zone Service
    private String status; // SEEDLING, GROWING, HARVESTED
}
