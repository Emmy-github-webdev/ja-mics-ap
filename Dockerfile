# Build one selected service from the multi-module project
FROM maven:3.9.8-eclipse-temurin-20 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY user-service/pom.xml user-service/
COPY product-service/pom.xml product-service/
COPY order-service/pom.xml order-service/
COPY payment-service/pom.xml payment-service/
COPY user-service/src user-service/src
COPY product-service/src product-service/src
COPY order-service/src order-service/src
COPY payment-service/src payment-service/src

ARG SERVICE_NAME=user-service
RUN mvn -pl ${SERVICE_NAME} -am clean package -DskipTests

FROM eclipse-temurin:20-jre
ARG SERVICE_NAME=user-service
ENV SERVICE_NAME=${SERVICE_NAME}
EXPOSE 8080
COPY --from=builder /workspace/${SERVICE_NAME}/target/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
