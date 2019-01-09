FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/jar/spring-boot-and-oauth2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
