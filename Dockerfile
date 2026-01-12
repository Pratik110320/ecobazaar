# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

# Run stage
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /target/EcoBazaar-module2-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]