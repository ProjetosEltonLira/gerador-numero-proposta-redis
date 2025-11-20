FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY ../target/gerar_numero_proposta_redis-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
EXPOSE 6379

ENTRYPOINT ["java","-jar","/app/app.jar"]