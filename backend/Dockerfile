# multi-stage builds, keep image size small
# location of Dockerfile are important
# COPY will ignore files/folders in .dockerignore
FROM gradle:6.8-jdk11 AS build
RUN mkdir -p /backend
WORKDIR /backend
COPY . .
RUN ./gradlew build

FROM openjdk:8-jre-alpine3.8
COPY --from=build /backend/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]