# Architecture & Design

## Architectural Style

Shopizer follows a **Layered Architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│                   (REST Controllers)                        │
│                      sm-shop/api                            │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                     Facade Layer                            │
│              (Orchestration & DTO Mapping)                  │
│                    sm-shop/facade                           │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   Business Logic Layer                      │
│                  (Domain Services)                          │
│                    sm-core/services                         │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   Data Access Layer                         │
│              (Spring Data Repositories)                     │
│                  sm-core/repositories                       │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      Database                               │
│                  (MySQL / PostgreSQL)                       │
└─────────────────────────────────────────────────────────────┘
```

## Layer Responsibilities

### 1. Presentation Layer (REST Controllers)

**Location**: `sm-shop/src/main/java/com/salesmanager/shop/store/api/v1/`

**Responsibilities**:
- Handle HTTP requests/responses
- Request validation
- Response formatting
- API versioning
- Swagger documentation

**Example**:
```java
@Controller
@RequestMapping("/api/v1")
public class ProductApi {
    
    @Inject
    private ProductFacade productFacade;
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ReadableProduct> getProduct(
        @PathVariable Long id,
        @ApiIgnore MerchantStore store,
        @ApiIgnore Language language
    ) {
        ReadableProduct product = productFacade.getProduct(id, store, language);
        return ResponseEntity.ok(product);
    }
}
```

### 2. Facade Layer (Service Orchestration)

**Location**: `sm-shop/src/main/java/com/salesmanager/shop/store/facade/`

**Responsibilities**:
- Orchestrate multiple service calls
- Convert between DTOs and Entities
- Handle cross-cutting concerns
- Transaction boundaries
- Business workflow coordination

**Example**:
```java
@Service
public class ProductFacadeImpl implements ProductFacade {
    
    @Inject
    private ProductService productService;
    
    @Inject
    private ReadableProductMapper productMapper;
    
    @Override
    public ReadableProduct getProduct(Long id, MerchantStore store, Language language) {
        Product product = productService.getById(id, store);
        return productMapper.convert(product, store, language);
    }
}
```

### 3. Business Logic Layer (Domain Services)

**Location**: `sm-core/src/main/java/com/salesmanager/core/business/services/`

**Responsibilities**:
- Core business logic
- Domain rules enforcement
- Entity lifecycle management
- Business validations
- Integration with external modules

**Example**:
```java
@Service
public class ProductServiceImpl implements ProductService {
    
    @Inject
    private ProductRepository productRepository;
    
    @Override
    public Product getById(Long id, MerchantStore store) {
        return productRepository.findByIdAndStore(id, store.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
```

### 4. Data Access Layer (Repositories)

**Location**: `sm-core/src/main/java/com/salesmanager/core/business/repositories/`

**Responsibilities**:
- Database operations (CRUD)
- Custom queries
- Data retrieval optimization
- Query methods

**Example**:
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByIdAndStore(Long id, Long storeId);
    
    @Query("SELECT p FROM Product p WHERE p.available = true AND p.merchantStore.id = :storeId")
    List<Product> findAvailableProducts(@Param("storeId") Long storeId);
}
```

## Request Flow Diagram

```
┌──────────┐
│  Client  │
│ (Browser)│
└──────────┘
     │
     │ HTTP Request
     │ GET /api/v1/products/123
     ▼
┌─────────────────────────────────────────────────────────────┐
│                    Spring Security Filter                   │
│  - JWT Token Validation                                     │
│  - Authentication                                           │
│  - Authorization                                            │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│                    ProductApi (Controller)                  │
│  @GetMapping("/products/{id}")                              │
│  - Extract path variables                                   │
│  - Validate request                                         │
│  - Resolve MerchantStore & Language                         │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│                  ProductFacade (Orchestration)              │
│  - Call ProductService                                      │
│  - Get related data (images, prices, inventory)            │
│  - Convert Entity → DTO (ReadableProduct)                   │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│                ProductService (Business Logic)              │
│  - Validate business rules                                  │
│  - Check product availability                               │
│  - Apply store-specific logic                               │
│  - Call ProductRepository                                   │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│              ProductRepository (Data Access)                │
│  - Execute JPA query                                        │
│  - Fetch from database                                      │
│  - Return Product entity                                    │
└─────────────────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────────┐
│                       Database                              │
│  SELECT * FROM PRODUCT WHERE ID = 123                       │
└─────────────────────────────────────────────────────────────┘
     │
     │ (Response flows back up)
     ▼
┌──────────┐
│  Client  │
│  JSON    │
└──────────┘
```

## Design Patterns

### 1. Repository Pattern
- Spring Data JPA repositories
- Abstraction over data access
- Custom query methods

### 2. Service Layer Pattern
- Business logic encapsulation
- Transaction management
- Service interfaces and implementations

### 3. Facade Pattern
- Simplify complex subsystems
- Coordinate multiple services
- DTO conversion

### 4. DTO Pattern
- Separate API models from domain models
- `PersistableProduct` (request)
- `ReadableProduct` (response)

### 5. Mapper Pattern
- Entity ↔ DTO conversion
- MapStruct-based mappers
- Legacy "Populator" pattern (being phased out)

### 6. Strategy Pattern
- Payment modules
- Shipping modules
- Email providers

### 7. Dependency Injection
- Constructor injection (`@Inject`)
- Field injection (`@Autowired`)
- Spring IoC container

## Multi-Tenancy Architecture

Shopizer supports **multi-store (multi-tenant)** architecture:

```
┌─────────────────────────────────────────────────────────────┐
│                    Single Database                          │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │ Store A      │  │ Store B      │  │ Store C      │     │
│  │ (Tenant 1)   │  │ (Tenant 2)   │  │ (Tenant 3)   │     │
│  │              │  │              │  │              │     │
│  │ Products     │  │ Products     │  │ Products     │     │
│  │ Orders       │  │ Orders       │  │ Orders       │     │
│  │ Customers    │  │ Customers    │  │ Customers    │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
│                                                             │
│  Data isolated by MerchantStore.id foreign key             │
└─────────────────────────────────────────────────────────────┘
```

**Key Points**:
- All entities have `merchantStore` reference
- Data filtered by store ID
- Store resolved from request (domain, header, or parameter)
- Shared reference data (countries, zones)

## Security Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                      Client Request                          │
│              Authorization: Bearer <JWT_TOKEN>               │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│              AuthenticationTokenFilter                       │
│  - Extract JWT from header                                   │
│  - Validate token signature                                  │
│  - Extract username from token                               │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│              UserDetailsService                              │
│  - Load user from database                                   │
│  - Load authorities/roles                                    │
│  - Create Authentication object                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│              SecurityContext                                 │
│  - Store authentication                                      │
│  - Available throughout request                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│              Method Security                                 │
│  @PreAuthorize("hasRole('ADMIN')")                          │
│  - Check permissions                                         │
│  - Allow/Deny access                                         │
└──────────────────────────────────────────────────────────────┘
```

## Module Architecture

Shopizer uses a **plugin-based module system** for integrations:

```
┌─────────────────────────────────────────────────────────────┐
│                    Core Application                         │
└─────────────────────────────────────────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   Payment    │    │   Shipping   │    │    Email     │
│   Module     │    │   Module     │    │   Module     │
└──────────────┘    └──────────────┘    └──────────────┘
        │                   │                   │
   ┌────┴────┐         ┌────┴────┐         ┌────┴────┐
   ▼         ▼         ▼         ▼         ▼         ▼
PayPal   Stripe   CanadaPost FedEx     SMTP    SendGrid
```

**Module Interface Example**:
```java
public interface PaymentModule {
    Transaction processPayment(Payment payment, Customer customer, MerchantStore store);
    Transaction refund(Transaction transaction);
    boolean validateConfiguration(IntegrationConfiguration config);
}
```

## Data Model Overview

```
┌──────────────────┐
│  MerchantStore   │ (Tenant/Store)
└──────────────────┘
         │
         │ 1:N
         ▼
┌──────────────────┐       ┌──────────────────┐
│    Product       │───────│    Category      │
└──────────────────┘  N:M  └──────────────────┘
         │
         │ 1:N
         ▼
┌──────────────────┐
│ ProductAttribute │ (Size, Color, etc.)
└──────────────────┘

┌──────────────────┐       ┌──────────────────┐
│    Customer      │───────│   ShoppingCart   │
└──────────────────┘  1:1  └──────────────────┘
         │                          │
         │ 1:N                      │ 1:N
         ▼                          ▼
┌──────────────────┐       ┌──────────────────┐
│      Order       │       │ ShoppingCartItem │
└──────────────────┘       └──────────────────┘
         │
         │ 1:N
         ▼
┌──────────────────┐
│   OrderProduct   │
└──────────────────┘
```

## Transaction Management

```
┌─────────────────────────────────────────────────────────────┐
│                    @Transactional                           │
│                   (Service Layer)                           │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  1. Begin Transaction                                │  │
│  │  2. Execute business logic                           │  │
│  │  3. Call multiple repositories                       │  │
│  │  4. Commit (success) or Rollback (exception)         │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

**Best Practices**:
- Transactions at service layer
- Read-only transactions for queries
- Proper exception handling
- Avoid long-running transactions

## Caching Strategy

```
┌──────────────────────────────────────────────────────────────┐
│                    Request                                   │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
                    ┌───────────────┐
                    │  Check Cache  │
                    └───────────────┘
                       │         │
                  Hit  │         │  Miss
                       ▼         ▼
              ┌──────────┐  ┌──────────┐
              │  Return  │  │  Query   │
              │  Cached  │  │  Database│
              └──────────┘  └──────────┘
                                  │
                                  ▼
                          ┌──────────────┐
                          │ Store in     │
                          │ Cache        │
                          └──────────────┘
```

**Cached Entities**:
- Products (frequently accessed)
- Categories
- Configuration
- Reference data

## Next Steps

Continue to [Project Structure](04-project-structure.md) to understand the codebase organization.
