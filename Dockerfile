FROM eclipse-temurin:21-jre-alpine

LABEL authors="JJM"

WORKDIR /app

COPY target/Clase-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]