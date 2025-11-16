FROM eclipse-temurin:17-jdk-jammy

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Create the working directory
WORKDIR /app

# Copy the complete source code
COPY . .

# Build the application with Maven
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Command to start the application
ENTRYPOINT ["java", "-jar", "target/questionapp-0.0.1-SNAPSHOT.jar"]