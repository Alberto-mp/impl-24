# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# The application's jar file
ARG JAR_FILE=cafeteria/target/cafeteria-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]