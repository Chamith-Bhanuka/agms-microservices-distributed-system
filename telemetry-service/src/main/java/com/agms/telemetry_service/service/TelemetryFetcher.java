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
    private final AutomationClient automationClient;
    private final WebClient.Builder webClientBuilder;
    private final AuthService authService; // Injected custom auth service

    @Value("${iot.external.url}")
    private String apiUrl;

    // Scheduled task runs every 10 seconds
    @Scheduled(fixedRateString = "${telemetry.fetch-interval}")
    public void fetchAndPushData() {
        log.info("Starting telemetry cycle...");

        authService.getAccessToken().subscribe(token -> {
            webClientBuilder.build()
                    .get()
                    // Replace with a deviceId from your database for 100% integration
                    .uri(apiUrl + "/devices/telemetry/{deviceId}", "b751b8c9-644a-484c-ba3f-be63f9b27ad0")
                    .header("Authorization", "Bearer " + token)
                .retrieve()
                    .bodyToMono(TelemetryData.class)
                    .subscribe(
                            data -> {
                                log.info("Data fetched: Temp {}°C from {}", data.getValue().getTemperature(), data.getDeviceId());
                                automationClient.pushTelemetryData(data); // Push to Automation
                            },
                            error -> log.error("Fetch Error: {}", error.getMessage())
                    );
        });
    }
}
