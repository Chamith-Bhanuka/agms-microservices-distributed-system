package com.agms.telemetry_service.service;

import com.agms.telemetry_service.client.AutomationClient;
import com.agms.telemetry_service.dto.TelemetryData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelemetryFetcher {
    private AutomationClient automationClient;
    private WebClient.Builder webClientBuilder; // Used for non-blocking IoT API calls

    // Runs every 10,000 milliseconds (10 seconds)
    @Scheduled(fixedRate = 10000)
    public void fetchAndPushData() {
        log.info("Fetching latest telemetry from External IoT API...");

        // 1. The Fetcher: Call External API (Requires Bearer Token)
        // Note: For now, we use a placeholder call to the External IoT API
        webClientBuilder.build()
                .get()
                .uri("http://104.211.95.241:8080/api/devices/telemetry/{deviceId}", "your-device-id")
                .header("Authorization", "Bearer your-token-here")
                .retrieve()
                .bodyToMono(TelemetryData.class)
                .subscribe(data -> {
                    log.info("Data received: Temp {}°C", data.getTemperature());

                    // 2. The Pusher: Immediately send to Automation Service
                    automationClient.pushTelemetryData(data);
                    log.info("Data pushed to Automation Service successfully.");
                });
    }
}
