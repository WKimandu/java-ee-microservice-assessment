FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/microservice-*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=production
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"] 