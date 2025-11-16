# Docker Configuration - Social Media App

## Docker Commands

### 1. Build and start the application
```bash
docker-compose up --build
```

### 2. Start in the background
```bash
docker-compose up -d
```

### 3. Stop the application
```bash
docker-compose down
```

### 4. Show logs
```bash
docker-compose logs -f spring-app
docker-compose logs -f mysql-db
```

### 5. Rebuild only the app
```bash
docker-compose build spring-app
docker-compose up spring-app
```

## Available Endpoints

After startup, the application is available at:
- **Application**: http://localhost:8080
- **Database**: localhost:3306

## Configuration

### Environment Variables
- `MYSQL_ROOT_PASSWORD`: from the .env file
- `MYSQL_DATABASE`: question
- `SPRING_PROFILES_ACTIVE`: docker

### Ports
- **Spring Boot**: 8080
- **MySQL**: 3306