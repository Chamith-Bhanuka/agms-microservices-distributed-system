package com.agms.zone_service.controller;

import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    @Autowired
    private ZoneService service; // Injecting the service

    @PostMapping
    public ResponseEntity<Zone> create(@RequestBody Zone zone) {
        // Business logic and validation happen inside the service
        return ResponseEntity.ok(service.createZone(zone));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getById(@PathVariable Long id) {
        // Use the service to find the zone instead of the repository directly
        return service.getZoneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
