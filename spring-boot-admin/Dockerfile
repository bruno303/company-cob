FROM openjdk:11.0.8-jre-slim-buster
WORKDIR /root/
COPY ./spring-boot-admin.jar .

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "./spring-boot-admin.jar"]