# Bookstore API

A RESTful API built with Spring Boot for managing a collection of books with JWT authentication.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)

## Features

### Core Features
- **User Authentication (JWT-based)**
  - User Signup (email, password)
  - User Login (returns JWT token)
  - Protected Routes for Books endpoints
- **Books Management**
  - Create a new book (title, author, category, price, rating, published date)
  - Get all books
  - Get book by ID
  - Update book by ID
  - Delete book by ID
- **Filtering & Search**
  - Filter books by author
  - Filter books by category
  - Filter books by price range
  - Search books by title (partial matches)
- **Error Handling**
  - Proper HTTP status codes
  - Input data validation

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Security** with JWT for authentication
- **Spring Data JPA** for database operations
- **PostgreSQL** for data storage
- **Maven** for dependency management and build

## Project Structure

```
📦 bookstore-api
 ┣ 📂 src/main/java/com/bookstore
 ┃ ┣ 📂 config
 ┃ ┃ ┗ JwtUtil.java (JWT Utility)
 ┃ ┣ 📂 controller
 ┃ ┃ ┣ AuthController.java (Signup/Login)
 ┃ ┃ ┗ BookController.java (CRUD + Filters)
 ┃ ┣ 📂 dto
 ┃ ┃ ┣ LoginRequest.java
 ┃ ┃ ┗ SignupRequest.java
 ┃ ┣ 📂 entity
 ┃ ┃ ┣ Book.java
 ┃ ┃ ┗ User.java
 ┃ ┣ 📂 repository
 ┃ ┃ ┣ BookRepository.java
 ┃ ┃ ┗ UserRepository.java
 ┃ ┣ 📂 security
 ┃ ┃ ┣ JwtFilter.java
 ┃ ┃ ┗ SecurityConfig.java
 ┃ ┣ 📂 service
 ┃ ┃ ┣ AuthService.java
 ┃ ┃ ┗ BookService.java
 ┃ ┗ BookstoreApiApplication.java (Main Class)
```

## Setup Instructions

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Database Setup
1. Create a PostgreSQL database named `bookstore`
2. Use the following DDL to create necessary tables:

```sql
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    rating DECIMAL(3,2) CHECK (rating >= 0 AND rating <= 5),
    published_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

### Application Setup
1. Clone this repository
   ```bash
   git clone https://github.com/nehashekhawat255/bookstore-api.git
   cd bookstore-api
   ```

2. Configure database connection in `src/main/resources/application.properties`
   ```properties
   server.port=8080
   
   spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   spring.datasource.driver-class-name=org.postgresql.Driver
   
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. Build the project
   ```bash
   mvn clean install -U
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

The server will start on port 8080 by default (or as configured in application.properties).

## API Documentation

### Authentication Endpoints

#### Register a new user
```
POST /api/auth/register
Content-Type: application/x-www-form-urlencoded

email=user@example.com&password=password123
```

#### Generate JWT token
```
POST /api/auth/token
Content-Type: application/x-www-form-urlencoded

email=user@example.com&password=password123
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQzMzI1MTYwLCJleHAiOjE3NDM0MTE1NjB9.ivVEc3uvEZHUEQ3VQF1fPJUH7ATr0J9KPiGf30t7s2M"
}
```

### Book Endpoints
All book endpoints require authentication with a valid JWT token in the Authorization header.

#### Create a new book
```
POST /api/books
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "category": "Fiction",
  "price": 10.99,
  "rating": 4.5,
  "publishedDate": "1925-04-10"
}
```

#### Get all books
```
GET /api/books
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Get a specific book by ID
```
GET /api/books/{id}
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Update a book
```
PUT /api/books/{id}
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "title": "Updated Title",
  "price": 12.99
}
```

#### Delete a book
```
DELETE /api/books/{id}
Authorization: Bearer YOUR_JWT_TOKEN
```

### Filtering and Search

#### Pagination and Sorting
```
GET /api/books?page=0&size=5&sortBy=title
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Filter by title
```
GET /api/books?title=Harry Potter&page=0&size=5&sortBy=price
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Filter by author
```
GET /api/books?author=J.K. Rowling&page=0&size=5&sortBy=id
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Filter by category
```
GET /api/books?category=Fantasy&page=0&size=10
Authorization: Bearer YOUR_JWT_TOKEN
```

#### Filter by price range
```
GET /api/books?minPrice=10&maxPrice=50&page=0&size=5
Authorization: Bearer YOUR_JWT_TOKEN
```

## Database Schema

### Books Table
| Column         | Type          | Constraints                                |
|----------------|---------------|-------------------------------------------|
| id             | SERIAL        | PRIMARY KEY                               |
| title          | VARCHAR(255)  | NOT NULL                                  |
| author         | VARCHAR(255)  | NOT NULL                                  |
| category       | VARCHAR(100)  | NOT NULL                                  |
| price          | DECIMAL(10,2) | NOT NULL                                  |
| rating         | DECIMAL(3,2)  | CHECK (rating >= 0 AND rating <= 5)       |
| published_date | DATE          | NOT NULL                                  |
| created_at     | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP                 |
| updated_at     | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP ON UPDATE       |

### Users Table
| Column   | Type         | Constraints            |
|----------|--------------|------------------------|
| id       | SERIAL       | PRIMARY KEY            |
| email    | VARCHAR(255) | UNIQUE NOT NULL        |
| password | VARCHAR(255) | NOT NULL               |
