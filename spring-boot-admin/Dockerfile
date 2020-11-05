FROM openjdk:11.0.8-jre-slim-buster
WORKDIR /root/
COPY ./spring-boot-admin.jar .

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./spring-boot-admin.jar"]