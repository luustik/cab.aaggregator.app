FROM maven:3.9.4-eclipse-temurin-17 AS build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN adduser --system ride-service && addgroup --system ride-service && adduser ride-service ride-service
USER ride-service

WORKDIR /app

COPY --from=build target/ride-service-0.0.1-SNAPSHOT.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]
