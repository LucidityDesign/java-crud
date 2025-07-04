# Use Eclipse Temurin (OpenJDK) base image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

RUN groupadd spring && useradd -m -g spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose application port (e.g., 8080)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
