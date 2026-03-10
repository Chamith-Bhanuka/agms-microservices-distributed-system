package com.agms.automation_service.dto;

import lombok.Data;

@Data
public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private Double temperature;
    private Double humidity;
}
