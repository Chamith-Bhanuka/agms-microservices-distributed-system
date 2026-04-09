package com.agms.zone_service.dto;

import lombok.Data;

@Data
public class DeviceRegistrationResponse {
    private String deviceId; // The UUID returned by the IoT API
    private String name;
    private String zoneId;
}
