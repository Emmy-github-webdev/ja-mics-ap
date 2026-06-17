# Ecommerce Microservices

This project contains 4 Java/Spring Boot microservices:
- `user-service` for registration and user lookup
- `product-service` for product CRUD and catalog operations
- `order-service` for checkout and order management
- `payment-service` for payment transaction processing

Each service uses AWS RDS-compatible PostgreSQL configuration and Redis caching.

## Build

From the root folder:

```bash
mvn -pl user-service,product-service,order-service,payment-service -am clean package
```

## Docker build

Build any service image by passing the service name:

```bash
docker build --build-arg SERVICE_NAME=user-service -t user-service:latest .
```

Example for payment service:

```bash
docker build --build-arg SERVICE_NAME=payment-service -t payment-service:latest .
```

## Environment

Each service reads database and Redis information from environment variables:

- `DB_HOST` - PostgreSQL/RDS endpoint
- `DB_PORT` - PostgreSQL port (default `5432`)
- `DB_NAME` - Database name for the service
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `REDIS_HOST` - Redis host
- `REDIS_PORT` - Redis port

## Service ports

- `user-service` -> `8081`
- `product-service` -> `8082`
- `order-service` -> `8083`
- `payment-service` -> `8084`

## Run
Locally, run the services individually

cd java-application/user-service
mvn clean install
mvn spring-boot:run

Or from the root project
mvn -pl user-service -am spring-boot:run

Access the project
curl http://localhost:8081/api/users
curl http://localhost:8082/api/products

Example with Docker:

```bash
docker run -e DB_HOST=your-rds-endpoint -e DB_PORT=5432 -e DB_NAME=userdb \
  -e DB_USERNAME=postgres -e DB_PASSWORD=postgres \
  -e REDIS_HOST=redis-host -e REDIS_PORT=6379 \
  -p 8081:8081 user-service:latest
```

Recreate the DB

& "C:\Program Files\PostgreSQL\16\bin\psql.exe" `
   -U postgres `
   -d paymentdb `
   -f paymentdb.sql

### .github/workflows/

1. feature-ci.yml

Responsibilities

- Compile
- Unit Tests
- Integration Tests
- Docker Build Validation
- Gitleaks
- CodeQL

2. pr-validation.yml

Responsibilities

- Unit Tests
- Integration Tests
- Coverage
- SonarQube
- Dependency Scan
- Quality Gates

3. release-dev.yml

Responsibilities

- Build JAR
- Build Image
- Trivy
- Push ECR
- Create GitOps Dev PR

4. promote-staging.yml

Responsibilities

Read image from Dev
Update Staging Overlay
Create GitOps Staging PR
Deploy Staging
Run Validation Tests

promote-prod.yml


ja-mics-ap/
├── .github/
│   └── workflows/
│       └── feature-ci.yml
├── product-service/
├── user-service/
├── payment-service/
└── order-service/