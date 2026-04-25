# Stage 1: сборка
FROM maven:3.9-eclipse-temurin-21 AS build

#RUN apt-get update && apt-get install -y nodejs npm && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn package -Pproduction -Dmaven.test.skip

# Stage 2: hardened runtime — zero CVE
FROM bellsoft/hardened-liberica-runtime-container:jre-21-glibc AS runner

WORKDIR /app
RUN addgroup -Sg 1000 appuser && adduser -SG appuser -u 1000 appuser

# --chown вместо отдельного RUN chown — не создаёт лишний слой
COPY --from=build --chown=appuser:appuser /app/target/*.jar service.jar

USER appuser

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "service.jar"]
