FROM maven:3.9.8-eclipse-temurin-21 AS build

ARG SERVICE_NAME

WORKDIR /app

COPY ${SERVICE_NAME}/pom.xml ./pom.xml
COPY ${SERVICE_NAME}/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN addgroup --system spring && adduser --system spring --ingroup spring

USER spring:spring

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]