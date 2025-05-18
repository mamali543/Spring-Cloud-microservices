# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set up workspace
WORKDIR /app

# Copy entire project (needed for proper Maven module resolution)
COPY pom.xml .
COPY common-dtos ./common-dtos
COPY covid-service ./covid-service
COPY countries-service ./countries-service
COPY aggregator-service ./aggregator-service
COPY api-gateway ./api-gateway
COPY eureka-service ./eureka-service
# Build only the api-gateway and its dependencies
RUN mvn clean package -DskipTests -pl api-gateway -am

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
# Use wildcard to handle version numbers automatically
COPY --from=builder /app/api-gateway/target/api-gateway-*.jar app.jar

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:80/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]