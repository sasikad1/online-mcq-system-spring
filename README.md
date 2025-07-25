# 🛠️ Online MCQ System – Backend (Spring Boot)

This is the backend REST API for the Online MCQ System developed as part of the internship assignment for SerendiLabs. It handles exam paper management, question data, submission handling, and result calculation.

---

## 📚 Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**
- **ModelMapper**
- **Maven**

---

## 🗂️ Project Structure

backend/<br>
├── controller/ # API endpoints<br>
├── dto/ # Data Transfer Objects<br>
├── entity/ # JPA Entities<br>
├── repository/ # Spring Data JPA Repos<br>
├── service/ # contract for impl<br>
├── service impl/ # Business Logic<br>
└── config/ # Config files (ModelMapper, etc.)


---

## 🔧 Setup Instructions

### 1️⃣ Prerequisites

- Java 17+
- MySQL
- Maven

---

### 2️⃣ Database Setup

- Create a MySQL database named: `mcq_system`

```sql
CREATE DATABASE `mcq_system`;   
```
---

### 3️⃣ Configure 
`application.properties`

Update the following values in:

`src/main/resources/application.properties`


```
spring.datasource.url=jdbc:mysql://localhost:3306/online_mcq
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8081

```
---
### 4️⃣ Run the Backend

```
./mvnw spring-boot:run
```
---
📥 API Endpoints
# API Endpoints

| Method | Endpoint                      | Description                     |
|--------|-------------------------------|---------------------------------|
| GET    | `/api/exams`                  | Get all exams                   |
| GET    | `/api/exams/{id}/questions`   | Get questions for an exam       |
| POST   | `/api/submit`                 | Submit answers & get result     |

---
### 🧪 Sample Data

You can insert mock users, exams, and questions using a data loader or manually through the database.  
Alternatively, you can use a simple init script in your service layer during application startup.

---

### 🧾 Sample Entities

- **User:** `id`, `name`, `email`
- **Exam:** `id`, `title`, `description`
- **Question:** `id`, `examId`, `questionText`, `options`, `correctOption`
- **Result:** `id`, `userId`, `examId`, `score`, `timestamp`
- **Answer:** `id`, `resultId`, `questionId`, `selectedOption`, `isCorrect`

---

### 🛠️ Tools Used

- Spring Initializr
- ModelMapper
- MySQL Workbench
- Postman – for testing

---

### 📬 Contact

Developed by **Sasika Dilum**  
📧 Email: [sasikadilum40@gmail.com](mailto:sasikadilum40@gmail.com)

---

### 🤝 Acknowledgement
Thanks to SerendiLabs for the opportunity to demonstrate my backend development skills through this assignment.
