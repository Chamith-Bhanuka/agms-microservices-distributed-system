package com.agms.crop_service.service;

import com.agms.crop_service.client.ZoneClient;
import com.agms.crop_service.entity.Crop;
import com.agms.crop_service.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {
    private final CropRepository repository;
    private final ZoneClient zoneClient; // Used for inter-service validation

    public Crop registerCrop(Crop crop) {
        // 1. Validation: Verify Zone exists in Zone Service via Feign
        try {
            zoneClient.checkZoneExists(crop.getZoneId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Zone ID: The specified zone does not exist.");
        }

        // 2. Business Rule: Initial status
        if (crop.getStatus() == null) {
            crop.setStatus("SEEDLING");
        }

        return repository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return repository.findAll();
    }

    public Crop updateStatus(Long id, String status) {
        return repository.findById(id).map(crop -> {
            crop.setStatus(status);
            return repository.save(crop);
        }).orElseThrow(() -> new RuntimeException("Crop not found"));
    }

    public void deleteCrop(Long id) {
        repository.deleteById(id);
    }
}
