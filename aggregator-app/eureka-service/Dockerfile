FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests -pl eureka-service -am

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY --from=builder /app/eureka-service/target/eureka-service-*.jar app.jar
EXPOSE 8761
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8761/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]