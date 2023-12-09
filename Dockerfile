FROM eclipse-temurin:17
VOLUME /tmp
EXPOSE 8081

WORKDIR /app

COPY /Users/numoh/Library/Mobile Documents/com~apple~CloudDocs/Desktop/KTHRepo/user-service/target/userservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
