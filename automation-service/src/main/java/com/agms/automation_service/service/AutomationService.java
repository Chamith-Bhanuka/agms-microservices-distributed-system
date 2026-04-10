package com.agms.automation_service.service;

import com.agms.automation_service.client.ZoneClient;
import com.agms.automation_service.dto.TelemetryData;
import com.agms.automation_service.dto.ZoneDTO;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.repository.AutomationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutomationService {
    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private AutomationLogRepository repository;

    public void evaluateTelemetry(TelemetryData data) {
        // 1. Fetch thresholds from Zone Service
        // Note: We use zoneId sent from Telemetry which originated in Zone Service
        ZoneDTO zone = zoneClient.getZoneDetails(Long.parseLong(data.getZoneId()));
        Double currentTemp = data.getValue().getTemperature();

        AutomationLog logEntry = new AutomationLog();
        logEntry.setZoneName(zone.getName());
        logEntry.setMeasuredValue(currentTemp);
        logEntry.setTimestamp(LocalDateTime.now());

        // 2. Rule Execution
        if (currentTemp > zone.getMaxTemp()) {
            logEntry.setAction("TURN_FAN_ON");
            repository.save(logEntry);
        } else if (currentTemp < zone.getMinTemp()) {
            logEntry.setAction("TURN_HEATER_ON");
            repository.save(logEntry);
        }
    }

    public List<AutomationLog> getAllLogs() {
        return repository.findAll();
    }
}
