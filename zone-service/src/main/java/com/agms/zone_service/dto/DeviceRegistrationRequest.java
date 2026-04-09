package com.agms.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceRegistrationRequest {
    private String name;
    private String zoneId;
}
