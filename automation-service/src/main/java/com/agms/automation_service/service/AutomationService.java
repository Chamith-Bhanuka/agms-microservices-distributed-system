package com.agms.automation_service.service;

import com.agms.automation_service.client.ZoneClient;
import com.agms.automation_service.dto.TelemetryData;
import com.agms.automation_service.dto.ZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationService {
    @Autowired
    private ZoneClient zoneClient;

    public void evaluateTelemetry(TelemetryData data) {
        // 1. Fetch safe limits from the Zone Service via Feign
        ZoneDTO zone = zoneClient.getZoneDetails(Long.parseLong(data.getZoneId()));

        // 2. The Rule Engine logic
        if (data.getTemperature() > zone.getMaxTemp()) {
            System.out.println(">>> ALERT: " + zone.getName() + " is TOO HOT (" + data.getTemperature() + "°C). Activating Fans!");
        } else if (data.getTemperature() < zone.getMinTemp()) {
            System.out.println(">>> ALERT: " + zone.getName() + " is TOO COLD (" + data.getTemperature() + "°C). Activating Heaters!");
        } else {
            System.out.println(">>> SUCCESS: " + zone.getName() + " is stable at " + data.getTemperature() + "°C.");
        }
    }
}
