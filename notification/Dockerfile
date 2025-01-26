# Build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:resolve

COPY src ./src
RUN mvn package -DskipTests

#Start
FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/notification-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
