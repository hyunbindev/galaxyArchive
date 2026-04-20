#  Amazon Corretto JDK 21
FROM amazoncorretto:21-al2023-jdk

WORKDIR /app

ARG JAR_FILE=api/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]