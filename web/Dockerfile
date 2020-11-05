FROM openjdk:11.0.8-jre-slim-buster
WORKDIR /root/
COPY ./web.jar .

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./web.jar"]