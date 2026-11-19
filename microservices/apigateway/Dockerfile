FROM gradle:8.10.2-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

# COPY target/*.jar app.jar
COPY --from=build /app/build/libs/*.jar app.jar


EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
