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
    private ZoneService service;

    @PostMapping
    public ResponseEntity<Zone> create(@RequestBody Zone zone) {
        return ResponseEntity.ok(service.createZone(zone));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getById(@PathVariable Long id) {
        return service.getZoneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}") //
    public ResponseEntity<Zone> update(@PathVariable Long id, @RequestBody Zone zone) {
        return ResponseEntity.ok(service.updateZone(id, zone));
    }

    @DeleteMapping("/{id}") //
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
