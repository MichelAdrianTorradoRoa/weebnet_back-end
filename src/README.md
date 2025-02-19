# **WeebNet - Backend**

WeebNet is a social network designed for anime and manga fans. This backend provides RESTful APIs to manage user authentication, posts, comments, and interactions. Built with **Spring Boot** and **PostgreSQL**, it implements secure authentication using JWT tokens.

---

## **🚀 Technologies Used**
- **Java 17** - Main programming language
- **Spring Boot** - Framework for backend development
- **Spring Security & JWT** - Secure authentication
- **PostgreSQL** - Relational database
- **Spring Data JPA** - Database management
- **Swagger** - API documentation

---

## **📂 Project Structure**
```
📦 weebnet-backend
 ┣ 📂 src/main/java/com/weebnet
 ┃ ┣ 📂 config          # Config
 ┃ ┣ 📂 controllers     # REST controllers
 ┃ ┣ 📂 dto             # DTO classes
 ┃ ┣ 📂 services        # Business logic
 ┃ ┣ 📂 exceptions      # Managament of Exceptions
 ┃ ┣ 📂 repositories    # Database access (JPA)
 ┃ ┣ 📂 entities        # Entity classes
 ┃ ┣ 📂 security        # JWT and authentication
 ┃ ┗ 📜 WeebNetApplication.java  # Main entry point
 ┣ 📂 src/main/resources
 ┃ ┣ 📜 application.properties  # Configuration file
 ┃ ┗ 📜 schema.sql              # Database schema
 ┣ 📜 pom.xml                   # Maven dependencies
 ┣ 📜 README.md                 # This file 📄
```

---

## **⚙️ Installation & Configuration**
### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/MichelAdrianTorradoRoa/weebnet_back-end.git
cd weebnet-backend
```

### **2️⃣ Setup PostgreSQL Database**
Ensure you have PostgreSQL installed and create a database:
```sql
CREATE DATABASE weebnet;
```

### **3️⃣ Configure `application.properties`**
Edit `src/main/resources/application.properties` with your database credentials:
```properties
spring.application.name=weebnet_backend

spring.datasource.url=jdbc:postgresql://localhost:5432/weebnet
spring.datasource.username=postgres
spring.datasource.password="your password"

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=org.postgresql.Driver
logging.level.org.springframework=INFO
logging.level.org.hibernate.SQL=DEBUG
management.info.env.enabled=true
management.endpoints.web.exposure.include=health,info



spring.security.user.name=Campus2023
spring.security.user.password=Campus2023

jwt.header=Authorization
jwt.token-prefix=Bearer
jwt.expiration=3600
jwt.secret=YBRt6IEvIMhGHIyuWWRfHZ6ib9kLLHNbNWJEFXjHJas=

spring.web.cors.allowed-origins=http://localhost:5173
```

### **4️⃣ Build and Run**
```bash
mvn clean install
mvn spring-boot:run
```
API will be available at `http://localhost:8080/api`.

---

## **🔑 Authentication & Security**
WeebNet uses **JWT (JSON Web Tokens)** for secure authentication. To access protected routes:

1. **Login** to get a JWT token.
2. **Send the token** in the `Authorization` header.

📍 **Login Example with `curl`**
```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"usernameOrEmail":"user","password":"123456"}'
```
📍 **Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```
📍 **Using Token for Protected Routes**
```bash
curl -X GET http://localhost:8080/api/users/login \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI..."
```

---

## **📡 Available Endpoints**

### **🔹 Authentication**
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login` | Login and obtain JWT |


Full API documentation is available in **Swagger** at `http://localhost:8080/swagger-ui.html/`.

---

## **🔗 Frontend Repository**
The frontend of WeebNet is available at:
👉 **[WeebNet Frontend Repository](https://github.com/MichelAdrianTorradoRoa/weebnet-frontend.git)**

### **🔍 Latest Commit Hashes**
#### **Backend**
```bash
git rev-parse HEAD
# Output example: a1b2c3d4e5f6g7h8i9j0k
```
#### **Frontend**
```bash
git -C ../weebnet-frontend rev-parse HEAD
# Output example: 913aeabe26ac2e746d195ab50783818148c47e2a
```

---

## **🐛 Common Issues & Solutions**
| Issue | Cause | Solution |
|-------|--------|-----------|
| `401 Unauthorized` | Missing or invalid JWT token | Ensure `Authorization: Bearer {token}` is set |
| `403 Forbidden` | No permission to access | Verify your user role |
| `Database connection error` | PostgreSQL is not running or credentials are incorrect | Check `application.properties` and DB status |

---
## **5️⃣ Demo Video**
A demonstration video showcasing the main functionalities of the application is available at the following link:
👉 **[Demo Video](YOUR_DRIVE_LINK_HERE)**
---

## **👨‍💻 Author**
**[Michel Adrian Torrado Roa]**  
GitHub: [@MichelAdrianTorradoRoa](https://github.com/MichelAdrianTorradoRoa)  
LinkedIn: [Michel Adrian Torrado](https://www.linkedin.com/in/michel-adrian-torrado-roa-2633941a2/)  

