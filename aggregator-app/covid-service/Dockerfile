FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY common-dtos ./common-dtos
COPY covid-service ./covid-service
COPY countries-service ./countries-service
COPY aggregator-service ./aggregator-service
COPY api-gateway ./api-gateway
COPY eureka-service ./eureka-service
RUN mvn clean package -DskipTests -pl covid-service -am

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/covid-service/target/covid-service-*.jar app.jar
# No EXPOSE/HEALTHCHECK: binds a random port at startup, reached only via Eureka by name.
ENTRYPOINT ["java", "-jar", "app.jar"]