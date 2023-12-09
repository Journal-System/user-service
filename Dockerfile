FROM eclipse-temurin:17
VOLUME /tmp
WORKDIR /user-service
COPY --from=build /user-service/target/userservice-0.0.1-SNAPSHOT.jar /user-service/userservice-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/user-service/userservice-0.0.1-SNAPSHOT.jar"]