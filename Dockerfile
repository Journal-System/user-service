FROM eclipse-temurin:17
VOLUME /tmp
EXPOSE 8081

ARG JAR_FILE=target/userservice-0.0.1-SNAPSHOT.jar
ADD /user-service/target/userservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
