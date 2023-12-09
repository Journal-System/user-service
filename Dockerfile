FROM eclipse-temurin:17
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/userservice-0.0.1-SNAPSHOT.jar /app/userservice-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/userservice-0.0.1-SNAPSHOT.jar"]