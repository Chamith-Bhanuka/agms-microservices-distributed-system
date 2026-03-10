package com.agms.automation_service.dto;

import lombok.Data;

@Data
public class ZoneDTO {
    private Long id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
}
