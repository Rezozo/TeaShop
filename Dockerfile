FROM gradle:8.5.0-jdk17 AS build
COPY . /home/gradle/app/
WORKDIR /home/gradle/app
RUN gradle build

FROM openjdk:17.0
COPY --from=build /home/gradle/app/build/libs/tea-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]