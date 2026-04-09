package com.agms.telemetry_service.service;

import com.agms.telemetry_service.client.AutomationClient;
import com.agms.telemetry_service.dto.TelemetryData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelemetryFetcher {
    @Autowired
    private AutomationClient automationClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Inject these from your config-repo/telemetry-service.yml
    @Value("${iot.external.auth-token}")
    private String authToken;

    @Value("${iot.external.url}")
    private String apiUrl;

    @Scheduled(fixedRateString = "${telemetry.fetch-interval}")
    public void fetchAndPushData() {
        log.info("Fetching latest telemetry...");

        webClientBuilder.build()
                .get()
                .uri(apiUrl + "/devices/telemetry/{deviceId}", "DEV_001")
                .header("Authorization", "Bearer " + authToken) // Use the real token
                .retrieve()
                .bodyToMono(TelemetryData.class)
                .subscribe(
                        data -> {
                            log.info("Data received: Temp {}°C", data.getTemperature());
                            automationClient.pushTelemetryData(data);
                        },
                        error -> log.error("Failed to fetch IoT data: {}", error.getMessage()) // Handle error so it doesn't crash
                );
    }
}
