package com.agms.telemetry_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class AuthService {

    @Value("${iot.external.url}")
    private String apiUrl;

    @Value("${iot.external.username}")
    private String username;

    @Value("${iot.external.password}")
    private String password;

    private String cachedAccessToken;
    private String cachedRefreshToken;

    /**
     * Standard entry point: Returns the cached token or triggers a new login.
     */
    public Mono<String> getAccessToken() {
        if (cachedAccessToken != null) {
            return Mono.just(cachedAccessToken);
        }
        return login();
    }

    /**
     * Authenticates with the external API using credentials.
     */
    public Mono<String> login() {
        log.info("Attempting login to external IoT API...");

        Map<String, String> loginRequest = Map.of(
                "username", username,
                "password", password
        );

        return WebClient.create(apiUrl)
                .post()
                .uri("/auth/login")
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    this.cachedAccessToken = (String) response.get("accessToken");
                    this.cachedRefreshToken = (String) response.get("refreshToken");
                    log.info("Login successful. Access token cached.");
                    return this.cachedAccessToken;
                });
    }

    /**
     * Refreshes the session using the stored refresh token.
     */
    public Mono<String> refreshAccessToken() {
        if (cachedRefreshToken == null) {
            log.warn("No refresh token found. Falling back to full login.");
            return login();
        }

        log.info("Refreshing access token using refresh token...");
        Map<String, String> refreshRequest = Map.of(
                "refreshToken", cachedRefreshToken
        );

        return WebClient.create(apiUrl)
                .post()
                .uri("/auth/refresh")
                .bodyValue(refreshRequest)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    this.cachedAccessToken = (String) response.get("accessToken");
                    this.cachedRefreshToken = (String) response.get("refreshToken");
                    log.info("Token refresh successful.");
                    return this.cachedAccessToken;
                })
                .onErrorResume(e -> {
                    log.error("Refresh failed: {}. Re-authenticating...", e.getMessage());
                    return login();
                });
    }
}