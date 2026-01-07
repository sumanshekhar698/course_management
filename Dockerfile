# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Render injects a $PORT variable, but Spring looks for 8080 by default.
# This command tells Spring to listen on whatever port Render provides.
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]