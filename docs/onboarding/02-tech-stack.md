# Tech Stack

## Core Technologies

### Backend Framework
- **Spring Boot 2.5.12**
  - Spring MVC (REST APIs)
  - Spring Data JPA (Data access)
  - Spring Security (Authentication & Authorization)
  - Spring AOP (Aspect-oriented programming)
  - Spring Cache (Caching abstraction)

### Java Version
- **Java 11** (minimum)
- **Java 17** (tested and supported)
- Uses Java 11 language features

### Build Tool
- **Maven 3.6+**
- Multi-module project structure
- Maven wrapper included (`mvnw`)

## Persistence Layer

### ORM & Database
- **Hibernate 5.x** - JPA implementation
- **Spring Data JPA** - Repository abstraction
- **HikariCP** - Connection pooling

### Supported Databases
| Database | Status | JDBC Driver |
|----------|--------|-------------|
| **H2** | Default (dev) | Embedded |
| **MySQL 8.x** | Production | `mysql-connector-java 8.0.21` |
| **PostgreSQL** | Production | `postgresql 42.2.18` |
| **Oracle** | Production | `ojdbc8 18.3.0.0` |

### Database Schema
- Schema name: `SALESMANAGER`
- Hibernate DDL: `update` (auto-creates/updates tables)

## API & Documentation

### REST API
- **Spring MVC** - REST controllers
- **Jackson 2.13.4** - JSON serialization/deserialization
- **Bean Validation** - Request validation (`javax.validation`)

### API Documentation
- **Swagger 2.9.2** (Springfox)
- **OpenAPI** specification
- Interactive UI at `/swagger-ui.html`

## Security

### Authentication & Authorization
- **Spring Security** - Core security framework
- **JWT (JSON Web Tokens)** - Token-based auth
  - Library: `jjwt 0.8.0`
- **BCrypt** - Password hashing

### Security Features
- Role-based access control (RBAC)
- Method-level security (`@PreAuthorize`)
- CORS support
- XSS protection

## Search

### Search Engine
- **Elasticsearch 7.5.2**
- Full-text product search
- Faceted search capabilities
- Custom analyzers for different languages

## Caching

### Cache Providers
- **Ehcache** - Default cache provider
- **Infinispan 9.4.18** - Distributed cache (optional)
- Spring Cache abstraction

### Cached Data
- Product catalog
- Categories
- Configuration
- Reference data (countries, zones)

## Business Rules

### Rules Engine
- **Drools 7.32.0** - Business rules engine
- Used for:
  - Shipping calculations
  - Tax rules
  - Pricing rules
  - Order totals

## Integrations & Modules

### Payment Gateways
- PayPal
- Stripe
- Extensible payment module system

### Shipping Providers
- Canada Post
- Custom shipping modules
- Flat rate, free shipping

### Email
- **SMTP** - Email sending
- **Thymeleaf** - Email templates
- HTML email support

### File Storage
- **Local File System** - Default
- **AWS S3** - Cloud storage
- **Google Cloud Storage** - Cloud storage
- Abstracted via CMS module

### Geolocation
- **MaxMind GeoIP2** - IP-based geolocation
- Used for tax/shipping calculations

## Utilities & Libraries

### Apache Commons
- `commons-lang3 3.5` - String/Object utilities
- `commons-io 2.7` - File/Stream utilities
- `commons-collections4 4.1` - Collection utilities
- `commons-validator 1.5.1` - Validation utilities

### Google Libraries
- **Guava 27.1** - Core utilities
- **Google Maps API** - Address validation

### Other Libraries
- **MapStruct 1.3.0** - Bean mapping
- **Apache HttpComponents 4.5.2** - HTTP client
- **Simple JSON 1.1.1** - JSON parsing

## Frontend (Separate Projects)

Shopizer backend is headless. Frontend options:
- **shopizer-admin** - React-based admin panel
- **shopizer-shop-reactjs** - React storefront example
- Build your own with any framework

## Development Tools

### Recommended IDEs
- **IntelliJ IDEA** (recommended)
- **Eclipse** with Spring Tools
- **VS Code** with Java extensions

### Testing
- **JUnit 5** - Unit testing
- **Spring Test** - Integration testing
- **Mockito** - Mocking framework

### Code Quality
- **Jacoco** - Code coverage
- Minimum coverage: 30% lines, 37% branches

## DevOps & Deployment

### Containerization
- **Docker** support
- Dockerfile included
- Docker Compose for local dev

### Cloud Platforms
- **AWS** - Configuration profiles included
- **Google Cloud Platform (GCP)** - Configuration profiles included
- Environment-specific configs

### CI/CD
- **CircleCI** - Continuous integration
- Configuration: `.circleci/config.yml`

## Technology Stack Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                        │
│                                                             │
│  Spring Boot 2.5.12  │  Spring MVC  │  Spring Security    │
│  ─────────────────────────────────────────────────────────  │
│  Swagger 2.9.2       │  JWT Auth    │  Bean Validation    │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                   Business Logic Layer                      │
│                                                             │
│  Spring Data JPA  │  Drools 7.32  │  Spring Cache         │
│  ─────────────────────────────────────────────────────────  │
│  MapStruct        │  Guava        │  Commons Utils        │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                    Persistence Layer                        │
│                                                             │
│  Hibernate 5.x    │  HikariCP     │  Ehcache              │
│  ─────────────────────────────────────────────────────────  │
│  MySQL / PostgreSQL / H2 / Oracle                          │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                   External Services                         │
│                                                             │
│  Elasticsearch 7.5.2  │  Payment Gateways  │  SMTP        │
│  ─────────────────────────────────────────────────────────  │
│  AWS S3 / GCS         │  Shipping APIs     │  GeoIP2      │
└─────────────────────────────────────────────────────────────┘
```

## Version Matrix

| Component | Version | Notes |
|-----------|---------|-------|
| Java | 11 / 17 | Minimum 11, tested with 17 |
| Spring Boot | 2.5.12 | |
| Hibernate | 5.x | Via Spring Boot |
| MySQL | 8.0+ | Recommended for production |
| Elasticsearch | 7.5.2 | Optional but recommended |
| Maven | 3.6+ | Build tool |

## Dependencies Overview

```xml
<!-- Core Framework -->
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-cache

<!-- Database -->
mysql-connector-java (8.0.21)
postgresql (42.2.18)
h2database (runtime)

<!-- Search -->
elasticsearch (7.5.2)

<!-- Caching -->
hibernate-ehcache
infinispan (9.4.18)

<!-- Security -->
jjwt (0.8.0)

<!-- API Documentation -->
springfox-swagger2 (2.9.2)
springfox-swagger-ui (2.9.2)

<!-- Business Rules -->
drools-core (7.32.0)

<!-- Utilities -->
guava (27.1-jre)
commons-lang3 (3.5)
mapstruct (1.3.0)
```

## Next Steps

Continue to [Architecture & Design](03-architecture.md) to understand how these technologies work together.
