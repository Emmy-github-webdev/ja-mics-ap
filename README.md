# Enterprise Commerce Platform

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Microservices%20E-Commerce-0A84FF?style=for-the-badge" alt="Platform" />
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.5.3" />
</p>

[![Build](https://github.com/Emmy-github-webdev/ja-mics-ap/actions/workflows/pr-validation.yml/badge.svg)](https://github.com/Emmy-github-webdev/ja-mics-ap/actions/workflows/pr-validation.yml)
[![Security](https://img.shields.io/badge/security-Gitleaks%20%2B%20CodeQL-8B5CF6)](https://github.com/Emmy-github-webdev/ja-mics-ap)
[![Coverage](https://img.shields.io/badge/coverage-JaCoCo%20enabled-success)](https://github.com/Emmy-github-webdev/ja-mics-ap)
[![Docker](https://img.shields.io/badge/Docker-supported-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![GitHub Repo](https://img.shields.io/badge/GitHub-ja--mics--ap-181717?logo=github)](https://github.com/Emmy-github-webdev/ja-mics-ap)

## Overview

This repository contains the application source code for a modular e-commerce platform implemented as a set of independently deployable Spring Boot microservices. The platform is designed to support a real-world enterprise delivery model where application code, infrastructure provisioning, and GitOps automation are managed across separate repositories.

The application stack includes:

- User registration and lookup
- Product catalog and inventory operations
- Order orchestration and checkout workflows
- Payment transaction processing
- PostgreSQL persistence for each domain service
- Redis-based caching for read-heavy workloads
- Docker support for containerized deployment
- CI/CD automation with GitHub Actions and deployment orchestration via Argo CD

## Why this architecture?

This design is intentional for enterprise-scale delivery and operational maturity:

- Independent service ownership and domain boundaries
- Faster release cycles per business capability
- Clear failure isolation between business domains
- Scalable deployment patterns for cloud-native environments
- Strong GitOps model for environment promotion and drift control
- Separation of application logic from infrastructure and deployment definitions

## Drawbacks

While the microservice pattern provides operational flexibility, it also introduces additional complexity:

- More moving parts than a monolith
- Increased operational burden for deployment, observability, and tracing
- Cross-service consistency challenges for data and transactions
- More complex local development and testing setups
- Network latency and distributed system failures must be handled explicitly

## Repository ecosystem

This project is part of a multi-repository platform model:

- Application source: [ja-mics-ap](https://github.com/Emmy-github-webdev/ja-mics-ap)
- Infrastructure repository: infrastructure repo for cloud and platform resources
- GitOps / Argo CD repository: kubernetes-argocd for environment deployment and reconciliation

This separation keeps application code, infrastructure provisioning, and deployment configuration independently governable while still operating as one cohesive platform.

## Architecture

The platform follows a domain-driven microservices architecture with asynchronous and synchronous service interactions between business domains.

```text
Client / API Consumer
        |
        v
[API Gateway / Entry Layer]
        |
        +--> user-service
        +--> product-service
        +--> order-service
        +--> payment-service

Service interactions:
- user-service -> PostgreSQL (users)
- product-service -> PostgreSQL (products) + Redis cache
- order-service -> PostgreSQL (orders) + Redis cache
- payment-service -> PostgreSQL (payments)

Shared platform services:
- PostgreSQL / RDS-compatible database
- Redis cache
- Docker runtime
- CI via GitHub Actions
- Deployment via Argo CD / Kubernetes
```

### Service map

- user-service: user registration and user lookup APIs
- product-service: product catalog, inventory, and retrieval operations
- order-service: checkout and order lifecycle management
- payment-service: payment transaction processing and settlement workflows

## Status

The project includes enterprise-grade automation for validation and quality assurance:

- GitHub Actions PR validation
- SonarQube-based code quality checks
- JaCoCo-based test coverage reporting
- Gitleaks secret scanning
- Docker image validation
- GitOps-driven deployment promotion

## Quick start

### Prerequisites

Before running the project locally, ensure you have the following installed:

- Java 21+
- Maven 3.9+
- Docker and Docker Compose (optional but recommended)
- PostgreSQL 16+
- Redis
- Git

### Clone the repository

```bash
git clone https://github.com/Emmy-github-webdev/ja-mics-ap.git
cd ja-mics-ap
```

### Build all services

```bash
mvn -pl user-service,product-service,order-service,payment-service -am clean package
```

### Run a single service

```bash
cd user-service
mvn clean install
mvn spring-boot:run
```

Or from the root project:

```bash
mvn -pl user-service -am spring-boot:run
```

## Installation

Each service uses environment variables for database and cache configuration.

Required environment variables:

- DB_HOST
- DB_PORT
- DB_NAME
- DB_USERNAME
- DB_PASSWORD
- REDIS_HOST
- REDIS_PORT

Example:

```bash
docker run -e DB_HOST=your-rds-endpoint -e DB_PORT=5432 -e DB_NAME=userdb \
  -e DB_USERNAME=postgres -e DB_PASSWORD=postgres \
  -e REDIS_HOST=redis-host -e REDIS_PORT=6379 \
  -p 8081:8081 user-service:latest
```

## Basic usage examples

### Service ports

- user-service: http://localhost:8081
- product-service: http://localhost:8082
- order-service: http://localhost:8083
- payment-service: http://localhost:8084

### Sample API calls

```bash
curl http://localhost:8081/api/users
curl http://localhost:8082/api/products
curl http://localhost:8083/api/orders
curl http://localhost:8084/api/payments
```

## Documentation

This repository includes supporting operational documentation alongside the codebase:

- [README1.md](README1.md) — project overview and delivery notes
- [troubleshoot.md](troubleshoot.md) — troubleshooting and operational guidance
- [postman/](postman/) — Postman collections for API validation

For broader platform context, this repository is designed to work with the infrastructure and Argo CD repositories that provision and reconcile environment resources.

## Contributing

Contributions are welcome for improvements in performance, resilience, security, observability, and platform hardening.

Recommended process:

1. Fork the repository
2. Create a feature branch
3. Make your changes with clear commit history
4. Run local validation and tests
5. Open a pull request against the target branch
6. Ensure CI passes and quality gates are satisfied

This project follows a disciplined engineering workflow intended for production-grade collaboration.

## License

This repository should include a formal open-source or internal-use license before production publication or public distribution. For an enterprise source-code release, Apache License 2.0 is the recommended default unless your organization has a different legal policy.

If your team has an approved license already, add the corresponding LICENSE file to the repository root and update this section to match it exactly.

## Technologies used

- Java 21
- Spring Boot 3.5.3
- Maven
- PostgreSQL
- Redis
- Flyway database migrations
- Spring Data JPA
- Spring Validation
- Spring Actuator
- Micrometer / Prometheus metrics
- Docker
- GitHub Actions
- SonarQube
- JaCoCo
- Argo CD / Kubernetes for deployment automation

## Project structure

```text
ja-mics-ap/
├── .github/
│   └── workflows/
├── db/
├── order-service/
├── payment-service/
├── postman/
├── product-service/
├── README.md
├── README1.md
├── troubleshoot.md
├── user-service/
└── ...
```

## Contact and ownership

This repository is maintained as part of a larger enterprise delivery platform, where the application source repository is connected to infrastructure provisioning and Kubernetes deployment automation repositories.

Repository:
- https://github.com/Emmy-github-webdev/ja-mics-ap

---

Built for scalable delivery, deployment governance, and enterprise-grade platform operations.
