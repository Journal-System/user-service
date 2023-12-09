FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f app/pom.xml install


FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/userservice-0.0.1-SNAPSHOT.jar /app/userservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/userservice-0.0.1-SNAPSHOT.jar"]