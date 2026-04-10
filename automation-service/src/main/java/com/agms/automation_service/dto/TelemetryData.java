package com.agms.automation_service.dto;

import lombok.Data;

@Data
public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private SensorValue value; // Matches the nested JSON from Telemetry Service

    @Data
    public static class SensorValue {
        private Double temperature;
        private Double humidity;
    }
}
