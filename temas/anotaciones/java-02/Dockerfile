# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Make port 6060 available to the world outside this container
EXPOSE 6060

# The application's jar file
ARG JAR_FILE=logger/target/logger-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]