FROM openjdk:22-jdk AS build
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:22-slim
COPY --from=build /app/build/libs/BANKAPI-0.0.1-SNAPSHOT.jar doc/app/BANKAPI-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/BANKAPI-0.0.1-SNAPSHOT.jar"]
