FROM maven:3.9.4-eclipse-temurin-17 AS build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN adduser --system discovery-service && addgroup --system discovery-service && adduser discovery-service discovery-service
USER discovery-service

WORKDIR /app

COPY --from=build target/discovery-service-0.0.1-SNAPSHOT.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]