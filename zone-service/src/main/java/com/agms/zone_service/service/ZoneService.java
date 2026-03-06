package com.agms.zone_service.service;

import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository repository;

    public Zone createZone(Zone zone) {
        // Rule: min Temp < max Temp
        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new IllegalArgumentException("Min temperature must be less than Max temperature");
        }

        // TODO: In a later step, we will use WebClient here to call
        // the External IoT API and set the deviceId
        zone.setDeviceId("TEMP_ID_FROM_IOT_API");

        return repository.save(zone);
    }

    public Optional<Zone> getZoneById(Long id) {
        return repository.findById(id);
    }
}
