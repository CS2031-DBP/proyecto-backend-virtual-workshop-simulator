FROM openjdk:22-jdk

WORKDIR /app

COPY target/proyecto-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]