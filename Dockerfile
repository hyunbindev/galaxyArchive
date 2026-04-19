#  Amazon Corretto JDK 21
FROM amazoncorretto:21-al2023-jdk

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 위치 변수 설정
ARG JAR_FILE=build/libs/*.jar

# JAR 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar

# 시간대 설정 (로그 시간 정합성)
ENV TZ=Asia/Seoul

# (prod 프로필 활성화)
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]