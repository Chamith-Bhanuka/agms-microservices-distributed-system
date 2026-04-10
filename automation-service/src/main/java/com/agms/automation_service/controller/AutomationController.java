package com.agms.automation_service.controller;

import com.agms.automation_service.dto.TelemetryData;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {
    @Autowired
    private AutomationService automationService;

    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody TelemetryData data) {
        automationService.evaluateTelemetry(data);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AutomationLog>> getLogs() {
        return ResponseEntity.ok(automationService.getAllLogs());
    }
}
