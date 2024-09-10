# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/your-spring-boot-app.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
