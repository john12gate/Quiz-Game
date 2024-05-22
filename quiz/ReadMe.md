# Quiz Game Application Backend

![GitHub stars](https://img.shields.io/github/stars/yourusername/quiz-game-backend)
![GitHub forks](https://img.shields.io/github/forks/yourusername/quiz-game-backend)
![GitHub issues](https://img.shields.io/github/issues/yourusername/quiz-game-backend)
![GitHub license](https://img.shields.io/github/license/yourusername/quiz-game-backend)

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
- PostgreSQL

### Installation

1. **Clone the Repository**
   ```sh
   git clone https://github.com/yourusername/quiz-game-backend.git
   cd quiz-game-backend
Configure Database
Update the application.properties file with your PostgreSQL database details:

properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
Build and Run the Application

sh
Copy code
mvn clean install
mvn spring-boot:run
API Endpoints
Home Endpoint
http
Copy code
GET /
Retrieve All Questions
http
Copy code
GET /api/questions
Response Codes:

200 OK: Successfully retrieved the list of questions.
400 Bad Request: Invalid request parameters.
500 Internal Server Error: Server error.
Retrieve Question by ID
http
Copy code
GET /api/questions/{id}
Response Codes:

200 OK: Successfully retrieved the question.
404 Not Found: Question not found.
500 Internal Server Error: Server error.
Add New Question
http
Copy code
POST /api/questions
Response Codes:

201 Created: Successfully created the question.
400 Bad Request: Invalid request body.
500 Internal Server Error: Server error.
Payload:

json
Copy code
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
Delete Question by ID
http
Copy code
DELETE /api/questions/{id}
Response Codes:

204 No Content: Successfully deleted the question.
404 Not Found: Question not found.
500 Internal Server Error: Server error.
Import Quiz Data from Excel
http
Copy code
POST /api/import/excel
Response Codes:

200 OK: Quiz data imported successfully.
400 Bad Request: Invalid data provided in the file.
500 Internal Server Error: Failed to import quiz data due to server error.
Payload:
Multipart file containing quiz data in the specified Excel format.

Download Excel Template
http
Copy code
GET /api/import/template
Response Codes:

200 OK: Template downloaded successfully.
500 Internal Server Error: Failed to download the template due to server error.
Export Quiz Data to Excel
http
Copy code
GET /api/export/excel
Response Codes:

200 OK: Successfully exported quiz data to Excel.
500 Internal Server Error: Internal server error occurred while exporting quiz data.
Excel Format
The Excel file should be formatted as follows:

Question	Category	Level	Option 1 Text	Correct 1	Explanation 1	Option 2 Text	Correct 2	Explanation 2	Option 3 Text	Correct 3	Explanation 3	Option 4 Text	Correct 4	Explanation 4
What is the capital of France?	Geography	Easy	Paris	Yes	Paris is the capital of France.	London	No	London is the capital of the United Kingdom.	Berlin	No	Berlin is the capital of Germany.	Madrid	No	Madrid is the capital of Spain.
Contributing
We welcome contributions from the community. Please follow these steps:

Fork the Repository
Create a Feature Branch
sh
Copy code
git checkout -b feature/YourFeatureBranchName
Commit Your Changes
sh
Copy code
git commit -m 'Add some feature'
Push to the Branch
sh
Copy code
git push origin feature/YourFeatureBranchName
Open a Pull Request
Running Tests
To run the tests for your Spring Boot application, including the ScoreServiceImplTest, you can use various tools and methods.

Using Maven
sh
Copy code
mvn test
Using Gradle
sh
Copy code
./gradlew test
Using an IDE
IntelliJ IDEA
Open the ScoreServiceImplTest class in IntelliJ IDEA.
Right-click anywhere in the test class.
Select Run 'ScoreServiceImplTest'.
Alternatively, you can run all tests in your project by right-clicking on the test directory or the project root and selecting Run 'All Tests'.

Eclipse
Open the ScoreServiceImplTest class in Eclipse.
Right-click anywhere in the test class.
Select Run As > JUnit Test.
Alternatively, you can run all tests in your project by right-clicking on the test directory or the project root and selecting Run As > JUnit Test.

Using Command Line with Spring Boot Plugin
If you're using the Spring Boot Maven or Gradle plugin, you can run the tests using the following commands:

Maven
sh
Copy code
mvn test
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgements
Special thanks to the incredible community of developers and contributors who make this project possible.

Feel free to explore our API documentation through the interactive Swagger UI and embark on a journey of creating an engaging quiz platform.

For further inquiries or support, please contact us at alionuche2008@gmail.com.

Happy Quizzing!

Connect with Us
Replace yourusername, yourdatabase, yourusername, yourpassword, and yourtwitterhandle with your actual details. This will give your README.md a professional and attractive appearance, enticing more views and engagement.
