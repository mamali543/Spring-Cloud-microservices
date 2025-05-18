FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY common-dtos ./common-dtos
COPY covid-service ./covid-service
COPY countries-service ./countries-service
COPY aggregator-service ./aggregator-service
COPY api-gateway ./api-gateway
COPY eureka-service ./eureka-service
RUN mvn clean package -DskipTests -pl countries-service -am

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/countries-service/target/countries-service-*.jar app.jar
EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8081/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]