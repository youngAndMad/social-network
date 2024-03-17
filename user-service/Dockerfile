FROM openjdk:21-jdk-slim
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]