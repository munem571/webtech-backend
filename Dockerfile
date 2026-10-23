FROM eclipse-temurin:25-jre-noble
WORKDIR /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
