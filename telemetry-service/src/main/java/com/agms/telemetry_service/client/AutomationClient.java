package com.agms.telemetry_service.client;

import com.agms.telemetry_service.dto.TelemetryData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-service") // Finds the service by name in Eureka
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    public // The internal endpoint will build later
    void pushTelemetryData(@RequestBody TelemetryData data);
}
