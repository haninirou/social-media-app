# Social Media App

A modern social media application built with Spring Boot and JWT authentication.

## Features

- **User registration & login** with JWT tokens
- **Create, edit & delete posts**
- **Add comments** to posts
- **Like system** for posts
- **REST API** with full CRUD functionality

## Technologies

- **Backend**: Spring Boot 3.0.2, Java 17
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT
- **Build tool**: Maven
- **Container**: Docker & Docker Compose

## Prerequisites

- Java 17
- MySQL 8.0
- Maven 3.6+
- Docker (optional)

## Installation & Start

### Run locally

```bash
# Clone the repository
git clone https://github.com/haninirou/social-media-app.git
cd social-media-app

# Configure the .env file (see .env.example)
cp .env.example .env

# Start the application
./mvnw spring-boot:run
```

### Run with Docker

```bash
# Start the Docker containers
docker-compose up --build

# Start in the background
docker-compose up -d
```

## API Endpoints

| Method | Endpoint         | Description         |
| ------ | ---------------- | ------------------- |
| POST   | `/auth/register` | User registration   |
| POST   | `/auth/login`    | User login          |
| GET    | `/posts`         | Get all posts       |
| POST   | `/posts`         | Create a new post   |
| POST   | `/comments`      | Add a comment       |
| POST   | `/likes`         | Like a post         |

## Example API Requests

### Registration

```
POST /auth/register
{
  "username": "testuser",
  "password": "password123"
}
```

### Create a post

```
POST /posts
{
  "title": "My post",
  "text": "Post content",
  "userId": 1
}
```

## Configuration

By default, the application runs on:

- **Port**: 8080
- **MySQL**: localhost:3306 (local) or 13306 (Docker)

## Environment Variables

Configured via the `.env` file:

```
MYSQL_ROOT_PASSWORD=your_password
MYSQL_DATABASE=question
QUESTION_APP_SECRET=your_jwt_secret
```
