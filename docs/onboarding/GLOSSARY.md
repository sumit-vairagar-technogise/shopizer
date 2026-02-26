# Glossary of Terms

A comprehensive glossary of terms, acronyms, and concepts used in Shopizer.

## General E-commerce Terms

**B2C (Business to Consumer)**
- Business model where companies sell directly to end consumers
- Example: Online retail stores

**B2B (Business to Business)**
- Business model where companies sell to other businesses
- Example: Wholesale platforms

**Headless Commerce**
- E-commerce architecture where the frontend (presentation layer) is decoupled from the backend
- Backend provides APIs that any frontend can consume
- Shopizer is a headless commerce platform

**Multi-Tenant / Multi-Store**
- Architecture supporting multiple independent stores/tenants in a single application instance
- Each store has isolated data but shares the same codebase
- In Shopizer: MerchantStore represents a tenant

**SKU (Stock Keeping Unit)**
- Unique identifier for a product variant
- Used for inventory tracking

## Shopizer-Specific Terms

**MerchantStore**
- Represents a store/tenant in the multi-store architecture
- Each store has its own products, customers, orders
- Identified by a unique code (e.g., "DEFAULT")

**Product**
- Core catalog entity representing an item for sale
- Can have variants, attributes, images, prices

**Category**
- Hierarchical classification for products
- Products can belong to multiple categories

**ProductAttribute**
- Product variations like size, color, material
- Composed of ProductOption (e.g., "Size") and ProductOptionValue (e.g., "Large")

**ReadableProduct**
- DTO (Data Transfer Object) for API responses
- Contains product data formatted for client consumption

**PersistableProduct**
- DTO for API requests
- Contains product data to be saved/updated

**Facade**
- Service orchestration layer between controllers and business services
- Handles DTO conversion and coordinates multiple service calls

**Populator**
- Legacy pattern for converting entities to DTOs
- Being replaced by Mappers

**Mapper**
- Modern pattern for entity ↔ DTO conversion
- Interface-based approach

## Technical Terms

**JPA (Java Persistence API)**
- Java specification for object-relational mapping (ORM)
- Hibernate is the implementation used in Shopizer

**Hibernate**
- ORM framework that implements JPA
- Maps Java objects to database tables

**Spring Data JPA**
- Spring abstraction over JPA
- Provides repository interfaces with automatic query generation

**Repository**
- Data access layer interface
- Extends Spring Data JPA repositories
- Example: `ProductRepository`

**Service**
- Business logic layer
- Contains domain logic and orchestrates repositories
- Example: `ProductService`

**Entity**
- JPA-annotated class representing a database table
- Example: `Product`, `Customer`, `Order`

**DTO (Data Transfer Object)**
- Object used to transfer data between layers
- Separates API models from domain models
- Examples: `ReadableProduct`, `PersistableProduct`

**REST (Representational State Transfer)**
- Architectural style for web services
- Uses HTTP methods (GET, POST, PUT, DELETE)
- Shopizer exposes REST APIs

**JWT (JSON Web Token)**
- Token-based authentication mechanism
- Used for securing API endpoints
- Contains encoded user information and signature

**Swagger / OpenAPI**
- API documentation specification
- Shopizer uses Swagger for interactive API docs
- Accessible at `/swagger-ui.html`

**Maven**
- Build automation and dependency management tool
- Shopizer uses Maven for project management

**POM (Project Object Model)**
- Maven configuration file (`pom.xml`)
- Defines dependencies, plugins, build configuration

**Spring Boot**
- Framework for building Java applications
- Provides auto-configuration and embedded server
- Shopizer is built on Spring Boot

**Spring Security**
- Security framework for authentication and authorization
- Handles JWT validation, role-based access control

**Spring MVC**
- Web framework for building REST APIs
- Used for Shopizer's controllers

**Dependency Injection (DI)**
- Design pattern where dependencies are provided to objects
- Spring IoC container manages dependencies
- Uses `@Inject` or `@Autowired` annotations

**IoC (Inversion of Control)**
- Design principle where framework controls object lifecycle
- Spring IoC container manages beans

**Bean**
- Object managed by Spring IoC container
- Annotated with `@Component`, `@Service`, `@Repository`, etc.

**Transaction**
- Unit of work that must be completed atomically
- Managed by `@Transactional` annotation
- Ensures data consistency

**CRUD (Create, Read, Update, Delete)**
- Basic operations for data management
- Repositories provide CRUD operations

## Database Terms

**Schema**
- Database namespace containing tables
- Shopizer uses schema: `SALESMANAGER`

**H2**
- Embedded Java database
- Default database for development
- In-memory or file-based

**MySQL**
- Popular open-source relational database
- Recommended for production

**PostgreSQL**
- Advanced open-source relational database
- Supported by Shopizer

**Connection Pool**
- Cache of database connections for reuse
- HikariCP is used in Shopizer
- Improves performance

**Migration**
- Process of updating database schema
- Hibernate auto-updates with `hbm2ddl.auto=update`

**Foreign Key**
- Column referencing primary key of another table
- Establishes relationships between tables
- Example: `merchant_id` in `PRODUCT` table

**Index**
- Database structure to speed up queries
- Created on frequently queried columns

**Query**
- Request to retrieve or manipulate data
- JPQL (Java Persistence Query Language) in Shopizer

## Architecture Terms

**Layered Architecture**
- Architectural pattern organizing code into layers
- Shopizer layers: Presentation, Facade, Service, Repository

**Separation of Concerns**
- Design principle dividing program into distinct sections
- Each layer has specific responsibility

**API Versioning**
- Managing different versions of APIs
- Shopizer has v1 and v2 APIs

**Microservices**
- Architectural style with loosely coupled services
- Shopizer is monolithic but can be split into microservices

**Monolithic**
- Single-tier application architecture
- All components in one deployable unit
- Shopizer's current architecture

**Facade Pattern**
- Design pattern providing simplified interface to complex subsystem
- Used in Shopizer's facade layer

**Repository Pattern**
- Design pattern abstracting data access
- Implemented via Spring Data repositories

**Strategy Pattern**
- Design pattern for selecting algorithm at runtime
- Used for payment/shipping modules

**Dependency Injection Pattern**
- Design pattern for loose coupling
- Core to Spring framework

## Security Terms

**Authentication**
- Process of verifying user identity
- Login with username/password

**Authorization**
- Process of verifying user permissions
- Role-based access control (RBAC)

**Role**
- Set of permissions assigned to users
- Examples: ADMIN, CUSTOMER, SUPERADMIN

**Permission**
- Specific access right
- Examples: READ, WRITE, DELETE

**CORS (Cross-Origin Resource Sharing)**
- Mechanism allowing restricted resources to be requested from another domain
- Configured in Shopizer for API access

**XSS (Cross-Site Scripting)**
- Security vulnerability allowing injection of malicious scripts
- Shopizer has XSS filters

**BCrypt**
- Password hashing algorithm
- Used for storing passwords securely

**Token**
- Credential used for authentication
- JWT tokens in Shopizer

**Bearer Token**
- Token type sent in Authorization header
- Format: `Authorization: Bearer <token>`

## Integration Terms

**Payment Gateway**
- Service processing credit card payments
- Examples: PayPal, Stripe

**Shipping Provider**
- Service calculating shipping costs and providing tracking
- Example: Canada Post

**CMS (Content Management System)**
- System for managing digital content
- Shopizer has CMS module for file storage

**SMTP (Simple Mail Transfer Protocol)**
- Protocol for sending emails
- Used for transactional emails

**Elasticsearch**
- Search and analytics engine
- Used for product search in Shopizer

**S3 (Simple Storage Service)**
- AWS cloud storage service
- Supported for file storage

**GCS (Google Cloud Storage)**
- Google cloud storage service
- Supported for file storage

**API (Application Programming Interface)**
- Interface for software components to communicate
- Shopizer exposes REST APIs

**Webhook**
- HTTP callback for event notifications
- Can be used for payment confirmations

## Development Terms

**IDE (Integrated Development Environment)**
- Software for writing code
- Examples: IntelliJ IDEA, Eclipse, VS Code

**Git**
- Version control system
- Used for source code management

**GitHub**
- Git repository hosting service
- Shopizer is hosted on GitHub

**Pull Request (PR)**
- Request to merge code changes
- Used for code review

**Branch**
- Parallel version of repository
- Used for feature development

**Commit**
- Snapshot of changes in Git
- Has unique identifier (hash)

**CI/CD (Continuous Integration/Continuous Deployment)**
- Automated build and deployment pipeline
- Shopizer uses CircleCI

**Docker**
- Containerization platform
- Shopizer can run in Docker containers

**Container**
- Lightweight, standalone executable package
- Includes application and dependencies

**Image**
- Template for creating containers
- Shopizer has Docker image

**Profile**
- Configuration set for different environments
- Examples: local, mysql, docker, aws, gcp

**Environment**
- Deployment context
- Examples: development, staging, production

## Testing Terms

**Unit Test**
- Test for individual component in isolation
- Uses mocking for dependencies

**Integration Test**
- Test for multiple components working together
- Uses real dependencies

**Mock**
- Simulated object for testing
- Mockito framework used in Shopizer

**JUnit**
- Testing framework for Java
- Used for unit tests

**Assertion**
- Statement verifying expected outcome
- Example: `assertEquals(expected, actual)`

**Test Coverage**
- Percentage of code executed by tests
- Jacoco measures coverage in Shopizer

## Performance Terms

**Cache**
- Temporary storage for frequently accessed data
- Ehcache used in Shopizer

**Caching**
- Storing data in cache for faster access
- Reduces database queries

**Connection Pooling**
- Reusing database connections
- HikariCP provides connection pool

**Lazy Loading**
- Loading data only when needed
- JPA entities use lazy loading for relationships

**Eager Loading**
- Loading data immediately
- Opposite of lazy loading

**Pagination**
- Dividing large result sets into pages
- Improves performance and user experience

**Index**
- Database structure for faster queries
- Should be added to frequently queried columns

## Acronyms Quick Reference

| Acronym | Full Form |
|---------|-----------|
| API | Application Programming Interface |
| AOP | Aspect-Oriented Programming |
| AWS | Amazon Web Services |
| B2B | Business to Business |
| B2C | Business to Consumer |
| CI/CD | Continuous Integration/Continuous Deployment |
| CMS | Content Management System |
| CORS | Cross-Origin Resource Sharing |
| CRUD | Create, Read, Update, Delete |
| DTO | Data Transfer Object |
| GCP | Google Cloud Platform |
| GCS | Google Cloud Storage |
| HTTP | Hypertext Transfer Protocol |
| HTTPS | HTTP Secure |
| IDE | Integrated Development Environment |
| IoC | Inversion of Control |
| JPA | Java Persistence API |
| JSON | JavaScript Object Notation |
| JWT | JSON Web Token |
| MVC | Model-View-Controller |
| ORM | Object-Relational Mapping |
| POM | Project Object Model |
| PR | Pull Request |
| PWA | Progressive Web App |
| RBAC | Role-Based Access Control |
| REST | Representational State Transfer |
| S3 | Simple Storage Service |
| SKU | Stock Keeping Unit |
| SMTP | Simple Mail Transfer Protocol |
| SQL | Structured Query Language |
| SSL | Secure Sockets Layer |
| TLS | Transport Layer Security |
| UI | User Interface |
| URL | Uniform Resource Locator |
| XSS | Cross-Site Scripting |

## Common Shopizer Packages

| Package | Purpose |
|---------|---------|
| `com.salesmanager.core.model` | JPA entities |
| `com.salesmanager.core.business.services` | Business services |
| `com.salesmanager.core.business.repositories` | Data repositories |
| `com.salesmanager.shop.store.api` | REST controllers |
| `com.salesmanager.shop.store.facade` | Service facades |
| `com.salesmanager.shop.model` | API DTOs |
| `com.salesmanager.shop.mapper` | Entity-DTO mappers |
| `com.salesmanager.shop.store.security` | Security components |

---

**Tip**: Bookmark this page for quick reference when reading code or documentation!
