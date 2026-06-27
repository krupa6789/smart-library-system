# Smart Library System - Spring Boot Application

A comprehensive enterprise library management system built with Spring Boot, demonstrating key concepts including Spring Boot fundamentals, RESTful Web Services, Spring Data JPA, Hibernate ORM, CRUD operations, and enterprise application development.

## Features

### 1. **Book Management**
   - Add, update, delete, and retrieve books
   - Search books by title, author, or category
   - Track available copies
   - Support for ISBN, publication details, and pricing

### 2. **Member Management**
   - Register library members
   - Manage member information
   - Track membership status
   - Monitor outstanding fines

### 3. **Borrowing System**
   - Borrow books (14-day borrowing period)
   - Return books with automatic fine calculation
   - Track overdue books
   - View borrowing history

### 4. **Fine Management**
   - Automatic fine calculation for overdue books
   - ₹10 per day fine
   - Track outstanding fines per member

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **ORM**: Hibernate
- **Database**: H2 (Development), MySQL (Production)
- **Build Tool**: Maven
- **Data Access**: Spring Data JPA
- **Validation**: Jakarta Validation (Bean Validation)
- **Lombok**: For reducing boilerplate code

## Project Structure

```
smart-library-system/
├── src/main/java/com/library/
│   ├── controller/         # REST Controllers
│   │   ├── BookController.java
│   │   ├── MemberController.java
│   │   └── BorrowingController.java
│   ├── service/            # Business Logic
│   │   ├── BookService.java
│   │   ├── MemberService.java
│   │   └── BorrowingService.java
│   ├── repository/         # Spring Data JPA Repositories
│   │   ├── BookRepository.java
│   │   ├── MemberRepository.java
│   │   └── BorrowingRepository.java
│   ├── entity/             # JPA Entities
│   │   ├── Book.java
│   │   ├── Member.java
│   │   └── Borrowing.java
│   ├── exception/          # Custom Exceptions
│   │   ├── ResourceNotFoundException.java
│   │   ├── InvalidOperationException.java
│   │   ├── ErrorDetails.java
│   │   └── GlobalExceptionHandler.java
│   └── SmartLibraryApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/krupa6789/smart-library-system.git
   cd smart-library-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080/library`

## API Endpoints

### Book Management

#### Create a Book
```bash
POST /library/api/books
Content-Type: application/json

{
  "title": "Spring in Action",
  "author": "Craig Walls",
  "isbn": "978-1617294945",
  "publisher": "Manning Publications",
  "category": "Technology",
  "totalCopies": 5,
  "price": 45.99,
  "description": "Master Spring Framework"
}
```

#### Get All Books
```bash
GET /library/api/books
```

#### Get Book by ID
```bash
GET /library/api/books/{id}
```

#### Search Books by Title
```bash
GET /library/api/books/search/title?title=Spring
```

#### Search Books by Author
```bash
GET /library/api/books/search/author?author=Craig
```

#### Get Available Books
```bash
GET /library/api/books/available
```

#### Update Book
```bash
PUT /library/api/books/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "author": "Author Name",
  "publisher": "Publisher Name",
  "category": "Technology",
  "price": 49.99
}
```

#### Delete Book
```bash
DELETE /library/api/books/{id}
```

### Member Management

#### Register Member
```bash
POST /library/api/members
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "address": "123 Main St",
  "dateOfBirth": "1990-05-15"
}
```

#### Get All Members
```bash
GET /library/api/members
```

#### Get Member by ID
```bash
GET /library/api/members/{id}
```

#### Get Member by Email
```bash
GET /library/api/members/email/john@example.com
```

#### Get Members with Pending Fines
```bash
GET /library/api/members/fine/pending
```

#### Update Member
```bash
PUT /library/api/members/{id}
Content-Type: application/json

{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "phone": "9876543210",
  "address": "456 Oak Ave"
}
```

#### Delete Member
```bash
DELETE /library/api/members/{id}
```

### Borrowing Management

#### Borrow Book
```bash
POST /library/api/borrowings/borrow/{memberId}/{bookId}
```

#### Return Book
```bash
PUT /library/api/borrowings/return/{borrowingId}
```

#### Get Member's Borrowings
```bash
GET /library/api/borrowings/member/{memberId}
```

#### Get Active Borrowings
```bash
GET /library/api/borrowings/member/{memberId}/active
```

#### Get Overdue Books
```bash
GET /library/api/borrowings/overdue
```

## Database Access

### H2 Console
Access the H2 database console at:
```
http://localhost:8080/library/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:librarydb`
- User Name: `sa`
- Password: (leave empty)

## Key Concepts Demonstrated

### 1. Spring Boot Fundamentals
- Auto-configuration
- Embedded web server
- Application properties
- Dependency injection

### 2. RESTful Web Services
- HTTP methods (GET, POST, PUT, DELETE)
- Request/Response handling
- Status codes
- Content negotiation

### 3. Spring Data JPA
- Repository pattern
- Custom queries using @Query
- Query methods
- CRUD operations

### 4. Hibernate ORM
- Entity mapping with JPA annotations
- Relationships (One-to-Many, Many-to-One)
- Cascade operations
- Lazy loading

### 5. CRUD Operations
- Create: Save new entities
- Read: Retrieve entities by ID or custom queries
- Update: Modify existing entities
- Delete: Remove entities

### 6. Enterprise Features
- Transaction management (@Transactional)
- Exception handling (Global Exception Handler)
- Validation (Bean Validation)
- Dependency injection
- Service layer pattern

## Configuration for Production

To switch to MySQL database, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_library
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Make sure MySQL is running and the database `smart_library` is created.

## Testing

You can test the API using:
- **Postman**: Import the endpoints above
- **cURL**: Command-line HTTP client
- **Spring Boot Test**: Unit and integration tests (can be added)

## Error Handling

The application includes comprehensive error handling:

- **400 Bad Request**: Invalid input or operation
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server-side errors

All errors return a standardized error response:

```json
{
  "errorCode": "ERROR_CODE",
  "errorMessage": "Descriptive error message",
  "timestamp": "2024-01-15T10:30:00"
}
```

## Future Enhancements

- User authentication and authorization (Spring Security)
- Reservation system for unavailable books
- Email notifications for due dates
- Advanced analytics and reporting
- Book ratings and reviews
- Integration with external APIs
- Pagination and sorting
- API documentation (Swagger/OpenAPI)

## License

This project is open source and available under the MIT License.

## Support

For issues and questions, please open an issue on GitHub.