FROM openjdk:21-jdk-slim-bullseye

WORKDIR /app-back

COPY target/*.jar /app-back/portfiolio.jar

ENTRYPOINT ["java", "-jar", "app-back/portfolio.jar"]