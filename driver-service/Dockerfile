FROM maven:3.9.4-eclipse-temurin-17 AS build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN adduser --system driver-service && addgroup --system driver-service && adduser driver-service driver-service
USER driver-service

WORKDIR /app

COPY --from=build target/driver-service-0.0.1-SNAPSHOT.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]
