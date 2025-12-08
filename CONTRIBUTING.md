# Contributing to Spring Boot E-commerce Application

Thank you for your interest in contributing to our Spring Boot e-commerce application! This document provides guidelines and instructions for contributing.

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Git
- IDE of your choice (IntelliJ IDEA, Eclipse, VS Code)

### Setting Up Development Environment

1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/springboot-ecommerce-application-tester.git
   cd springboot-ecommerce-application-tester
   ```

3. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

4. Install dependencies:
   ```bash
   mvn clean install
   ```

## Development Workflow

### Code Style
- Follow Java coding conventions
- Use meaningful variable and method names
- Add appropriate comments for complex logic
- Ensure proper exception handling

### Testing
- Write unit tests for all new functionality
- Ensure all tests pass before submitting:
  ```bash
  mvn test
  ```
- Aim for high test coverage (>80%)

### Commit Guidelines
- Use clear, descriptive commit messages
- Follow conventional commit format:
  ```
  type(scope): description
  
  [optional body]
  
  [optional footer]
  ```
- Types: feat, fix, docs, style, refactor, test, chore

### Pull Request Process

1. Ensure your code follows the style guidelines
2. Update documentation if needed
3. Add tests for new functionality
4. Ensure all CI checks pass
5. Request review from maintainers

## CI/CD Pipeline

Our project uses GitLab CI for continuous integration. The pipeline includes:
- Code compilation and build
- Unit and integration tests
- Code quality analysis
- Security scanning
- Container image building

## Reporting Issues

When reporting issues, please include:
- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Environment details (Java version, OS, etc.)
- Relevant logs or error messages

## Questions?

If you have questions, feel free to:
- Open an issue for discussion
- Contact the maintainers
- Check existing documentation

Thank you for contributing!