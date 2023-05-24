FROM openjdk:11-jdk-slim

ENV LANG=C.UTF-8

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew build

COPY build/libs/download-executor-11.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
