package com.agms.crop_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {
    @GetMapping("/api/zones/{id}")
    Object checkZoneExists(@PathVariable Long id);
}
