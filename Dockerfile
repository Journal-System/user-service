FROM eclipse-temurin:17
EXPOSE 8081
WORKDIR /user-service
ARG JAR_FILE=target/userservice-0.0.1-SNAPSHOT.jar
ADD /user-service/target/userservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
