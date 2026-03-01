# 🌱 Automated Greenhouse Management System (AGMS)

The **AGMS** is a cloud-native, microservice-based platform built with **Spring Boot** and **Spring Cloud** to modernize agricultural precision.  
The architecture leverages:

- **Netflix Eureka** for service discovery
- **Spring Cloud Config** for centralized property management
- **Spring Cloud Gateway** secured with **JWT authorization**
- Integration with a **Live External IoT Data Provider** via **Spring WebFlux** to fetch real-time telemetry
- A custom **Rule Engine** that triggers automated environmental actions

---

## ⚙️ Core Infrastructure

- **Service Registry**: Netflix Eureka for dynamic service registration
- **Config Server**: Centralized configuration management via Git repository
- **API Gateway**: Single entry point for routing and JWT-based security
- **Inter-Service Communication**: Synchronous calls using OpenFeign and RestTemplate

---

## 🧩 Domain Microservices

| Service              | Port | Responsibility |
|----------------------|------|----------------|
| **Zone Management**  | 8081 | Defines environmental limits and registers IoT devices |
| **Sensor Telemetry** | 8082 | Fetches live IoT data every 10 seconds and pushes it to Automation |
| **Automation & Control** | 8083 | Rule Engine that triggers actions based on zone thresholds |
| **Crop Inventory**   | 8084 | Manages plant lifecycles from Seedling to Harvest |

---

## 🚀 Startup Instructions

To ensure proper service discovery and configuration, start services in the following order:

1. **Config Server** → Provides properties to all other services
2. **Eureka Server** → Allows services to register themselves
3. **API Gateway** → Establishes the secure entry point
4. **Domain Services** → Start Zone, Telemetry, Automation, and Crop services (any order once infrastructure is UP)

---

## 📦 Project Artifacts

- **Postman Collection**: Located in the root directory for API testing
- **Eureka Dashboard**: Screenshot available at `docs/eureka-status.png` showing all services as UP

---