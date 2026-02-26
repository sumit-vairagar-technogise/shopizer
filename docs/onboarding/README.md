# Shopizer Onboarding Guide

Welcome to Shopizer! This guide will help you understand the project structure, architecture, and get you up and running.

## Table of Contents

1. [Project Overview](01-project-overview.md)
2. [Tech Stack](02-tech-stack.md)
3. [Architecture & Design](03-architecture.md)
4. [Project Structure](04-project-structure.md)
5. [Getting Started](05-getting-started.md)
6. [Testing Guide](06-testing-guide.md)
7. [API Guide](07-api-guide.md)
8. [Database Schema](08-database-schema.md)
9. [Development Workflow](09-development-workflow.md)

## Quick Start

If you want to jump right in:

```bash
# Clone and build
git clone <repository-url>
cd shopizer
mvn clean install

# Run the application
cd sm-shop
mvn spring-boot:run

# Access Swagger UI
open http://localhost:8080/swagger-ui.html
```

## Prerequisites

- Java 11 or higher (tested with Java 11, 17)
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Postman or similar API testing tool (optional)

## Learning Path

### Week 1: Understanding the Basics
- Read Project Overview and Tech Stack
- Set up local environment
- Explore Swagger UI and test basic APIs
- Understand the project structure

### Week 2: Deep Dive
- Study the architecture and design patterns
- Trace a complete request flow (Product API)
- Understand database schema
- Review security implementation

### Week 3: Hands-On
- Make your first code change
- Add a new API endpoint
- Write tests
- Submit your first PR

## Getting Help

- Check the [FAQ](FAQ.md)
- Review existing code examples
- Ask the team on Slack

## Contributing

See [Development Workflow](08-development-workflow.md) for contribution guidelines.
