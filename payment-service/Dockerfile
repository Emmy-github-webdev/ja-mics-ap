FROM maven:3.9.8-eclipse-temurin-21 AS build

ARG SERVICE_NAME

WORKDIR /app

COPY ${SERVICE_NAME}/pom.xml ./pom.xml
COPY ${SERVICE_NAME}/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN groupadd --gid 10001 spring && \
    useradd \
      --uid 10001 \
      --gid spring \
      --create-home \
      --shell /usr/sbin/nologin \
      spring

USER 10001:10001

EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]