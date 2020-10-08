FROM openjdk:11.0.8-jre-slim-buster
WORKDIR /root/
COPY ./web.jar .

EXPOSE 9000
ENTRYPOINT ["java", "-jar", "./web.jar"]