FROM eclipse-temurin:17
VOLUME /tmp
EXPOSE 8081
ADD target/userservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]