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
        private SensorValue value;
        private String capturedAt;

        @Data
        public static class SensorValue {
            private Double temperature;
            private String tempUnit;
            private Double humidity;
            private String humidityUnit;
        }
}
