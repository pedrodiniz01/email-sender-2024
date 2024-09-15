# Use a base image with JDK 17 for ARM architecture
FROM arm32v7/eclipse-temurin:17-jdk-focal

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/emailsender-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
