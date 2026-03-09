package com.agms.telemetry_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private Double temperature;
    private Double humidity;
    private String capturedAt;
}
