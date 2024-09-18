# Step 1: Build the application
FROM gradle:7.3.3-jdk11 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Step 2: Create a smaller image with just the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]