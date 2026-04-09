package com.agms.telemetry_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthService {
    @Value("${iot.external.url}")
    private String apiUrl;

    @Value("${iot.external.username}")
    private String username;

    @Value("${iot.external.password}")
    private String password;

    private String cachedToken;

    public Mono<String> getAccessToken() {
        if (cachedToken != null) return Mono.just(cachedToken);

        // Payload matches POST /auth/login
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
                    cachedToken = (String) response.get("accessToken");
                    return cachedToken;
                });
    }
}
