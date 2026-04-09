package com.agms.zone_service.service;

import com.agms.zone_service.dto.DeviceRegistrationRequest;
import com.agms.zone_service.dto.DeviceRegistrationResponse;
import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository repository;

    @Value("${iot.external.url}")
    private String iotApiUrl;

    @Value("${iot.external.username}")
    private String username;

    @Value("${iot.external.password}")
    private String password;

    public Zone createZone(Zone zone) {
        // Business Rule: min Temp strictly < max Temp
        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new IllegalArgumentException("Min temperature must be strictly less than Max temperature");
        }

        // 1. Get Token from External IoT API
        String token = WebClient.create(iotApiUrl)
                .post()
                .uri("/auth/login")
                .bodyValue(Map.of("username", username, "password", password))
                .retrieve()
                .bodyToMono(Map.class)
                .map(res -> res.get("accessToken").toString())
                .block();

        // 2. Register Device with External IoT API
        DeviceRegistrationResponse deviceResponse = WebClient.create(iotApiUrl)
                .post()
                .uri("/devices")
                .header("Authorization", "Bearer " + token)
                .bodyValue(new DeviceRegistrationRequest(zone.getName(), zone.getName()))
                .retrieve()
                .bodyToMono(DeviceRegistrationResponse.class)
                .block();

        // 3. Set the returned deviceId and save locally
        if (deviceResponse != null) {
            zone.setDeviceId(deviceResponse.getDeviceId());
        }

        return repository.save(zone);
    }

    public Zone updateZone(Long id, Zone zoneDetails) {
        return repository.findById(id).map(zone -> {
            zone.setMinTemp(zoneDetails.getMinTemp());
            zone.setMaxTemp(zoneDetails.getMaxTemp());
            return repository.save(zone);
        }).orElseThrow(() -> new RuntimeException("Zone not found"));
    }

    public void deleteZone(Long id) {
        repository.deleteById(id);
    }

    public Optional<Zone> getZoneById(Long id) {
        return repository.findById(id);
    }
}
