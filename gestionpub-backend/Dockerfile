# Use a base image with Java
FROM openjdk:8-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR into the container
COPY target/gestionpub.jar ./gestionpub.jar

# Expose the port on which the app runs
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "gestionpub.jar"]