FROM maven:3.8.1-openjdk-17-slim AS builder

WORKDIR /app
COPY . .

RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-slim

COPY .env .

COPY --from=builder /app/target/Bank-web-application.jar web-app.jar

CMD ["java", "-jar", "web-app.jar"]