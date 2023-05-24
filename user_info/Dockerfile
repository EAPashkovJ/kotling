FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY ../gradlew .

COPY src ./src

RUN chmod +x ./gradlew
RUN ./gradlew build

COPY build/libs/your-app.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
