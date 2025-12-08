# Spring Boot E-commerce Application

This is the README for the feature/feature-1 branch.

## Overview

A comprehensive Spring Boot e-commerce application demonstrating modern Java development practices, RESTful API design, and enterprise-grade architecture patterns.

## Features

- RESTful API endpoints for product management
- Spring Boot 3.x with Java 17
- Maven build system
- Comprehensive test coverage
- CI/CD pipeline integration

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9.x
- Git

### Building the Application

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

## Development

This project follows standard Spring Boot conventions and includes:

- Layered architecture (Controller, Service, Repository)
- Unit and integration tests
- Maven dependency management
- Configuration externalization

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.