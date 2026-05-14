# ===============================
# Stage 1: Build
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven wrapper + config
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Give permission
RUN chmod +x mvnw

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build jar
RUN ./mvnw clean package -DskipTests


# ===============================
# Stage 2: Run
# ===============================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

RUN mkdir -p /app/uploads

# Expose port
EXPOSE 8080

# JVM optimization (optional but good)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run app with profile support
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]