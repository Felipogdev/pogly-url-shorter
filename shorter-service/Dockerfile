# Etapa 1: build usando Gradle
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app

COPY . .

RUN gradle build -x test

# Etapa 2: imagem final mais leve
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
