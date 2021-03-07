FROM openjdk:8-jdk-alpine
COPY target/cliente-server-1.0.2.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]