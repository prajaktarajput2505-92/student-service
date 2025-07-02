# Start with a lightweight base image with Java
FROM eclipse-temurin:17-jdk-alpine

# Create a directory for the app
WORKDIR /app

# Copy JAR file into container
COPY target/*.jar app.jar
COPY application-docker.properties application.properties

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
