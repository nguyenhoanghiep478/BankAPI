FROM openjdk:22-jdk AS build
COPY . .
RUN gradlew clean package -DskipTests

FROM openjdk:22-slim
COPY --from=build /target/BANKAPI-0.0.1-SNAPSHOT.jar BANKAPI.jar\
EXPOSE 8080
ENTRYPOINT["java","-jar","BANKAPI.jar"]
