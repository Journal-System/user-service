FROM eclipse-temurin:17
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]