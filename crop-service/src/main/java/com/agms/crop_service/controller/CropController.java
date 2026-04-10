package com.agms.crop_service.controller;

import com.agms.crop_service.entity.Crop;
import com.agms.crop_service.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropService service;

    @PostMapping
    public ResponseEntity<Crop> create(@RequestBody Crop crop) {
        return ResponseEntity.ok(service.registerCrop(crop));
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAll() {
        return ResponseEntity.ok(service.getAllCrops());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Crop> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}
