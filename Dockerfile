# ==============================
# Stage 1: Build Spring Boot app
# ==============================

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy project files into container
COPY . .

# Give Maven wrapper execute permission (needed on Linux)
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests


# ==============================
# Stage 2: Run Spring Boot app
# ==============================

FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR created in Stage 1
COPY --from=build /app/target/*.jar app.jar

# Spring Boot port
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]