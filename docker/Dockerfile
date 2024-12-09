FROM maven:3.9.4-eclipse-temurin-17 AS build

ARG SERVICE_NAME

COPY ${SERVICE_NAME}/pom.xml pom.xml
COPY ${SERVICE_NAME}/src src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

ARG SERVICE_NAME

RUN adduser --system ${SERVICE_NAME} && addgroup --system ${SERVICE_NAME} && adduser ${SERVICE_NAME} ${SERVICE_NAME}
USER ${SERVICE_NAME}

WORKDIR /app

COPY --from=build target/*.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]