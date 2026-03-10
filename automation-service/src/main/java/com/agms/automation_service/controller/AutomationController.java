package com.agms.automation_service.controller;

import com.agms.automation_service.dto.TelemetryData;
import com.agms.automation_service.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {
    @Autowired
    private AutomationService automationService;

    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody TelemetryData data) {
        automationService.evaluateTelemetry(data);
    }
}
