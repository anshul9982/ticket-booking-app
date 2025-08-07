# Spring Boot Train Ticket Booking API

This project is a robust, secure, and scalable RESTful API for a train ticket booking system, built with Java and the Spring Boot framework. It demonstrates modern backend development practices, including JWT-based security, transactional data management, concurrency control, and performance optimization with Redis caching.

---

## ✨Features :

- **Secure User Authentication**: Stateless user registration and login system using JWT (JSON Web Tokens).
- **Role-Based Authorization**: Endpoints are protected using Spring Security, ensuring only authenticated users can access sensitive operations.
- **Train Search**: Publicly accessible endpoint to search for available trains between a source and destination.
- **Concurrent-Safe Ticket Booking**: A transactional booking process that uses pessimistic database locking (`PESSIMISTIC_WRITE`) to prevent race conditions and ensure a seat is never double-booked.
- **Booking History**: Authenticated users can view a list of all their booked tickets.
- **Ticket Cancellation**: Users can cancel their own bookings, which correctly releases the seat and makes it available for others.
- **Performance Caching**: Integrated **Redis** to cache train search results, significantly reducing database load and improving response times for frequent queries.
- **Automated Data Seeding**: The application automatically populates the in-memory H2 database with sample trains and seats on startup for easy testing and development.

---
## 🛠️ Tech Stack & Tools

<p align="left">
  <a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot">
  </a>
  <a href="https://www.java.com" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  </a>
  <a href="https://spring.io/projects/spring-security" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Security">
  </a>
  <a href="https://jwt.io" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT">
  </a>
  <a href="https://spring.io/projects/spring-data-jpa" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Data JPA">
  </a>
  <a href="https://redis.io/" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  </a>
  <a href="https://www.h2database.com" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/H2_Database-464646?style=for-the-badge&logo=h2&logoColor=white" alt="H2 Database">
  </a>
  <a href="https://gradle.org/" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
  </a>
  <a href="https://projectlombok.org/" target="_blank" rel="noreferrer">
    <img src="https://img.shields.io/badge/Lombok-B80B5A?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok">
  </a>
</p>

---

## 🚀 Getting Started

### Prerequisites

- Java 21 (or later)
- Gradle 8.x
- A running Redis instance (e.g., via Docker: `docker run -p 6379:6379 -d redis`)

### Installation & Running

1.  **Clone the repository:**
    ```sh
    git clone <your-repository-url>
    cd ticketbooking
    ```

2.  **Run the application:**
    You can run the application using the Gradle wrapper.
    ```sh
    ./gradlew bootRun
    ```
    The application will start on `http://localhost:8080`.

3.  **Access the H2 Console:**
    The in-memory database console is available for inspecting the data.
    - URL: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:ticketdb`
    - Username: `sa`
    - Password: `password`

---

## 📋 API Endpoints

The base URL is `http://localhost:8080`.

### Authentication

| Method | Endpoint         | Protection | Description                               |
| :----- | :--------------- | :--------- | :---------------------------------------- |
| `POST` | `/user/register` | Public     | Registers a new user.                     |
| `POST` | `/user/login`    | Public     | Authenticates a user and returns a JWT.   |

### Trains

| Method | Endpoint               | Protection | Description                               |
| :----- | :--------------------- | :--------- | :---------------------------------------- |
| `GET`  | `/trains/search-trains`| Public     | Searches for trains by source and destination. |

### Bookings (Requires JWT Bearer Token)

| Method   | Endpoint                     | Protection   | Description                               |
| :------- | :--------------------------- | :----------- | :---------------------------------------- |
| `POST`     | `/api/bookings/book-ticket`  | Authenticated| Books a ticket for the logged-in user.    |
| `GET`      | `/api/bookings/my-bookings`  | Authenticated| Retrieves the booking history for the user.|
| `DELETE`   | `/api/bookings/{ticketId}`   | Authenticated| Cancels a specific booking by its ID.     |

### Example Request Body for Booking

**POST** `/api/bookings/book-ticket`

```json
{
  "trainId": "EXP-001",
  "source": "Mumbai",
  "destination": "Pune"
}
```

---

## 🏛️ Architectural Highlights

*   **Stateless JWT Authentication**: The API uses JWT for authentication, ensuring that the server remains stateless and can be easily scaled horizontally.
*   **Transactional Integrity**: All database write operations, especially booking and cancellation, are wrapped in `@Transactional` blocks to ensure data consistency. If any part of the operation fails, the entire transaction is rolled back.
*   **Concurrency Control**: To handle the classic "race condition" problem where two users might try to book the last seat simultaneously, the application uses a `PESSIMISTIC_WRITE` database lock. This ensures that only one transaction can access a potential seat at a time, guaranteeing data integrity.
*   **Service-Oriented Design**: The application logic is cleanly separated into controllers, services, and repositories, following the principles of Separation of Concerns for better maintainability and testability.
