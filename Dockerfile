# Step 1: Build the application with JDK 17
FROM gradle:7.3.3-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Step 2: Create a smaller image with JRE 17
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
