FROM maven:3.9-eclipse-temurin-17 AS build
COPY ../src/main/java/com/splitbill/splitbill .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]