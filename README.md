# 🍽️ Mealyfy - Meal Discovery and Management API

## 📌 Overview
Mealyfy is a **Spring Boot** application that allows users to **discover meals**, **filter recipes**, and **store meal information** using both external API data and a local database.

## 🚀 Features
- **Discover Meals**: Fetch meals from an external API.
- **Filter by Name, Area, and Ingredients**.
- **Store Meals Locally**: Add meals to a PostgreSQL database.
- **Retrieve Stored Meals**: View meals saved in the database.

## 🏗️ Architecture
- **Spring Boot** backend.
- **PostgreSQL** as the database.
- **Docker Compose** for service orchestration.
- **Third-party API Integration** for external meal data.

## 🛠️ Requirements
To run this application, ensure you have:
- **Docker** installed.
- **Docker Compose** installed.

## 🏗️ Setup & Run
1️⃣ Clone the repository:
```sh
 git clone https://github.com/your-repo/mealyfy.git
 cd mealyfy
```

2️⃣ Start the application using run script with **Docker Compose** from root dir:
```sh
 sh start_mealyfy.sh
```

This will:
✅ Build and start the Spring Boot application.  
✅ Start a PostgreSQL database container.  
✅ Expose the application on `http://localhost:8081`.

## 🛠️ API Endpoints
### **🌍 External API Calls** (Fetched from a third-party API)
- **Get a random meal:**  
  `GET http://localhost:8081/api/v1/meals/external/random`

- **Filter meals by name, area, or ingredient:**  
  `GET http://localhost:8081/api/v1/meals/external/filter?area=Italian&name=Spaghetti`

### **📦 Database Operations**
- **Get all stored meals:**  
  `GET http://localhost:8081/api/v1/meals`

- **Add a meal by ID (stores from external API to database):**  
  `POST http://localhost:8081/api/v1/meals/add/{mealId}`

  Example:
  ```sh
  POST http://localhost:8081/api/v1/meals/add/53038
  ```

## 🛠️ Configuration
The application uses environment variables for database configuration:
```yaml
services:
  springboot-app:
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgresqldb:5432/mealdb
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

## 🧪 Running Tests
Run the unit tests using:
```sh
mvn test
```

---
🚀 **Now, explore meals and enjoy cooking with Mealyfy!**

