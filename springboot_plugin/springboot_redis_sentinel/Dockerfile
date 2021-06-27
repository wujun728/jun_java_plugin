FROM openjdk:8-jre-alpine

ARG JAR_FILE

COPY target/${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
