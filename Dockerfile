# ========= BUILD STAGE =========
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy the project files to the container
COPY . .

# Give execute permissions to the mvnw script
RUN chmod +x ./mvnw

# Run Maven build
RUN ./mvnw -q -DskipTests clean package

# ========= RUNTIME STAGE =========
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
