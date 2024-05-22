# Quiz Game Application Backend

![GitHub stars](https://img.ields.io/github/stars/john12gate/quiz-game) ![GitHub forks](https://img.ields.io/github/forks/john12gate/quiz-game) ![GitHub issues](https://img.ields.io/github/issues/john12gate/quiz-game) ![GitHub license](https://img.ields.io/github/license/john12gate/quiz-game)

Welcome to the **Quiz Game Application Backend**, a sophisticated and comprehensive API designed to manage quiz questions, track scores, and provide a seamless experience for developers and quiz enthusiasts alike.

## Overview

Embark on a journey of knowledge and fun with our state-of-the-art quiz platform. This backend service is meticulously crafted to offer robust and efficient management of quiz-related operations, ensuring an unparalleled user experience. Whether you are a developer looking to integrate quiz functionalities or an enthusiast eager to explore endless possibilities, our API serves as the perfect foundation.

## Features

- **Manage Quiz Questions**: Create, retrieve, update, and delete quiz questions with ease.
- **Track Scores**: Maintain and access a record of quiz scores, including top scores.
- **Pagination Support**: Efficiently handle large sets of quiz questions with pagination.
- **Error Handling**: Comprehensive error handling to ensure smooth and reliable operations.
- **Swagger UI**: Interactive API documentation for easy exploration and testing.
- **Import and Export**: Seamlessly import and export quiz data using Excel files.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- Spring Boot
- Maven
- H2 Database

### Installation

1. **Clone the Repository**
   ```
   git clone https://github.com/john12gate/quiz-game.git
   cd quiz-game
   
## Configure Database

Update the `application.yaml` file with your H2 database details:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:quiz-app-db
    driver-class-name: org.h2.Driver
    username: sa
    password: password
    hikari:
      pool-name: HikariCP
      maximum-pool-size: 10
      minimum-idle: 15
      idle-timeout: 30000
      connection-timeout: 20000
      max-lifetime: 1800000
      connection-test-query: SELECT 1
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true
        jdbc:
          batch_size: 50
          fetch_size: 50
  open-in-view: false
liquibase:
  enabled: true
  change-log: classpath:/db/changelog/db.changelog-master.yaml

logging:
  level:
    org:
      hibernate:
        SQL: DEBUG
        type:
          descriptor:
            sql:
              BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

server:
  port: 8080
  servlet:
    context-path: /
  compression:
    enabled: true
    mime-types: application/json,application/xml,text/html,text/xml,text/plain
    min-response-size: 2048

security:
  basic:
    enabled: false
  ignored: /h2-console/**

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: always

## Build Project

mvn clean install      # for Maven Build
mvn spring-boot:run    # For Spring Boot Build

### API Endpoints
Explore the interactive API documentation at Swagger UI.
http://localhost:8080/swagger-ui/


### Payload to Create a New Quiz

{
  "questionText": "What is the capital of France?",
  "category": "Geography",
  "level": "Easy",
  "options": [
    {
      "optionText": "Paris",
      "isCorrect": true,
      "explanation": "Paris is the capital of France."
    },
    {
      "optionText": "London",
      "isCorrect": false,
      "explanation": "London is the capital of the United Kingdom."
    },
    {
      "optionText": "Berlin",
      "isCorrect": false,
      "explanation": "Berlin is the capital of Germany."
    },
    {
      "optionText": "Madrid",
      "isCorrect": false,
      "explanation": "Madrid is the capital of Spain."
    }
  ]
}

### Contributing
We welcome contributions from the community. Please follow these steps:

## Fork the Repository

- **Create a Feature Branch
git checkout -b feature/YourFeatureBranchName

- **Commit Your Changes
git commit -m 'Add some feature'

- **Push to the Branch
git push origin feature/YourFeatureBranchName


### Running Tests
To run the tests for your Spring Boot application, including the ScoreServiceImplTest, you can use various tools and methods:

Using Maven
mvn test

## Using an IDE

### IntelliJ IDEA

```console
1. Open the `ScoreServiceImplTest` class in IntelliJ IDEA.
2. Right-click anywhere in the test class.
3. Select `Run 'ScoreServiceImplTest'`.
4. Alternatively, you can run all tests in your project by right-clicking on the test directory or the project root and selecting `Run 'All Tests'`.


### Eclipse
1. Open the `ScoreServiceImplTest` class in Eclipse.
2. Right-click anywhere in the test class.
3. Select `Run As > JUnit Test`.
4. Alternatively, you can run all tests in your project by right-clicking on the test directory or the project root and selecting `Run As > JUnit Test`.


### License
This project is licensed under the MIT License - see the LICENSE file for details.

### Acknowledgements
Special thanks to the incredible community of developers and contributors who make this project possible.

Feel free to explore our API documentation through the interactive Swagger UI and embark on a journey of creating an engaging quiz platform.

For further inquiries or support, please contact us at alionuche2008@gmail.com.

### Happy Quizzing!

### Connect with Us
GitHub: [john12gate](https://github.com/john12gate)
LinkedIn: [john-ali-software-developer](https://www.linkedin.com/in/john-ali-software-developer/)
Medium: [internetgurus](https://medium.com/@internetgurus)
