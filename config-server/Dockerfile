FROM maven:3.9.4-eclipse-temurin-17 AS build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN adduser --system config-server && addgroup --system config-server && adduser config-server config-server
USER config-server

WORKDIR /app

COPY --from=build target/config-server-0.0.1-SNAPSHOT.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]