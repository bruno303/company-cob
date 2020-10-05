# Stage 1 (to create a "build" image, ~140MB)
FROM maven:3.6.3-jdk-11-slim AS builder
RUN java -version

COPY . /usr/src/companycob/
WORKDIR /usr/src/companycob/
# RUN apk --no-cache add maven && mvn --version
RUN mvn clean install

# Stage 2 (to create a downsized "container executable", ~87MB)
FROM openjdk:11.0.8-jre-slim-buster
WORKDIR /root/
COPY --from=builder /usr/src/companycob/assembly/target/companycob/web/web.jar .

EXPOSE 9000
ENTRYPOINT ["java", "-jar", "./web.jar"]