# Stage 1: Build stage
FROM openjdk:22-jdk-slim AS build

# Cài đặt các gói cần thiết, bao gồm findutils để có lệnh xargs
RUN apt-get update && apt-get install -y \
    findutils \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .

RUN chmod +x ./gradlew
# Chạy lệnh build và ghi lại log
RUN ./gradlew clean build --info -x test > build.log 2>&1 || (cat build.log && exit 1)

# Stage 2: Package stage
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/BANKAPI-0.0.1-SNAPSHOT.jar /app/BANKAPI-0.0.1-SNAPSHOT.jar
<<<<<<< HEAD
EXPOSE 8031
=======
EXPOSE 8080
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3

ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "/app/BANKAPI-0.0.1-SNAPSHOT.jar"]