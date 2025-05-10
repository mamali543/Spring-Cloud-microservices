# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set up a workspace that mimics the project structure for Maven's relativePath
WORKDIR /project

# Copy the parent pom.xml to the root of our build workspace
COPY ../pom.xml ./pom.xml

# Create the module directory and copy its contents
WORKDIR /project/eureka-service
COPY pom.xml ./pom.xml
COPY src ./src

# Package the application. Maven will find the parent POM at ../pom.xml
# (relative to /project/eureka-service/pom.xml)
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /project/eureka-service/target/eureka-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]