# Visual Overview - Shopizer Architecture

This document provides visual representations of Shopizer's architecture to help you understand how everything fits together.

## Complete System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CLIENT APPLICATIONS                               │
│                                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Web App    │  │  Mobile App  │  │     PWA      │  │   Admin UI   │  │
│  │  (React/     │  │  (iOS/       │  │              │  │   (React)    │  │
│  │   Angular)   │  │   Android)   │  │              │  │              │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ HTTPS / REST API (JSON)
                                    │
┌─────────────────────────────────────────────────────────────────────────────┐
│                         SHOPIZER BACKEND (sm-shop)                          │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                      PRESENTATION LAYER                               │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │   Product   │  │   Order     │  │  Customer   │  │   Store    │  │ │
│  │  │     API     │  │    API      │  │    API      │  │    API     │  │ │
│  │  │  (v1, v2)   │  │  (v1, v2)   │  │  (v1, v2)   │  │  (v1, v2)  │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  │                                                                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐   │ │
│  │  │  Spring Security Filter Chain                                │   │ │
│  │  │  - JWT Authentication                                        │   │ │
│  │  │  - Authorization                                             │   │ │
│  │  │  - CORS                                                      │   │ │
│  │  └──────────────────────────────────────────────────────────────┘   │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                    │                                        │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                        FACADE LAYER                                   │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │   Product   │  │    Order    │  │  Customer   │  │   Store    │  │ │
│  │  │   Facade    │  │   Facade    │  │   Facade    │  │   Facade   │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  │                                                                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐   │ │
│  │  │  Mappers (Entity ↔ DTO Conversion)                           │   │ │
│  │  └──────────────────────────────────────────────────────────────┘   │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
┌─────────────────────────────────────────────────────────────────────────────┐
│                       BUSINESS LOGIC LAYER (sm-core)                        │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                         SERVICE LAYER                                 │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │   Product   │  │    Order    │  │  Customer   │  │   Store    │  │ │
│  │  │   Service   │  │   Service   │  │   Service   │  │  Service   │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  │                                                                       │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │  Shipping   │  │   Payment   │  │     Tax     │  │   Search   │  │ │
│  │  │   Service   │  │   Service   │  │   Service   │  │  Service   │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  │                                                                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐   │ │
│  │  │  @Transactional Boundary                                     │   │ │
│  │  └──────────────────────────────────────────────────────────────┘   │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                    │                                        │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                      REPOSITORY LAYER                                 │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │   Product   │  │    Order    │  │  Customer   │  │   Store    │  │ │
│  │  │ Repository  │  │ Repository  │  │ Repository  │  │ Repository │  │ │
│  │  │   (JPA)     │  │   (JPA)     │  │   (JPA)     │  │   (JPA)    │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
┌─────────────────────────────────────────────────────────────────────────────┐
│                      DATA LAYER (sm-core-model)                             │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                       JPA ENTITIES                                    │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │   Product   │  │    Order    │  │  Customer   │  │   Store    │  │ │
│  │  │   @Entity   │  │   @Entity   │  │   @Entity   │  │  @Entity   │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  │                                                                       │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌────────────┐  │ │
│  │  │  Category   │  │    Cart     │  │   Payment   │  │  Shipping  │  │ │
│  │  │   @Entity   │  │   @Entity   │  │   @Entity   │  │  @Entity   │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └────────────┘  │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ Hibernate / JPA
                                    │
┌─────────────────────────────────────────────────────────────────────────────┐
│                            DATABASE LAYER                                   │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                    MySQL / PostgreSQL / H2                            │ │
│  │                    Schema: SALESMANAGER                               │ │
│  │                                                                       │ │
│  │  Tables: PRODUCT, CATEGORY, CUSTOMER, ORDERS, MERCHANT_STORE, etc.   │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                        EXTERNAL INTEGRATIONS                                │
│                                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Elasticsearch│  │   Payment    │  │   Shipping   │  │     Email    │  │
│  │   (Search)   │  │   Gateways   │  │   Providers  │  │   Service    │  │
│  │              │  │  (PayPal,    │  │  (Canada     │  │   (SMTP)     │  │
│  │              │  │   Stripe)    │  │   Post)      │  │              │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  │
│                                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   AWS S3     │  │     GCS      │  │    GeoIP2    │  │    Drools    │  │
│  │  (Storage)   │  │  (Storage)   │  │  (Location)  │  │   (Rules)    │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Request Flow - Product API Example

```
┌──────────┐
│  Client  │ GET /api/v1/products/123?store=DEFAULT&lang=en
└──────────┘
     │
     │ 1. HTTP Request
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Spring Security Filter                                     │
│  - Extract JWT token                                        │
│  - Validate token                                           │
│  - Load user authorities                                    │
│  - Set SecurityContext                                      │
└─────────────────────────────────────────────────────────────┘
     │
     │ 2. Authenticated Request
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductApi (Controller)                                    │
│  @GetMapping("/products/{id}")                              │
│  - Extract path variable: id=123                            │
│  - Resolve MerchantStore: DEFAULT                           │
│  - Resolve Language: en                                     │
│  - Call facade                                              │
└─────────────────────────────────────────────────────────────┘
     │
     │ 3. Delegate to Facade
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductFacade                                              │
│  - Orchestrate service calls                                │
│  - Get product from ProductService                          │
│  - Get images from ProductImageService                      │
│  - Get prices from PricingService                           │
│  - Get inventory from InventoryService                      │
│  - Convert Entity → DTO using Mapper                        │
└─────────────────────────────────────────────────────────────┘
     │
     │ 4. Business Logic
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductService                                             │
│  @Transactional(readOnly=true)                              │
│  - Validate business rules                                  │
│  - Check product availability                               │
│  - Apply store-specific logic                               │
│  - Call repository                                          │
└─────────────────────────────────────────────────────────────┘
     │
     │ 5. Data Access
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductRepository (Spring Data JPA)                        │
│  findByIdAndStore(123, storeId)                             │
│  - Generate SQL query                                       │
│  - Execute query                                            │
│  - Map ResultSet to Entity                                  │
└─────────────────────────────────────────────────────────────┘
     │
     │ 6. Database Query
     ▼
┌─────────────────────────────────────────────────────────────┐
│  Database (MySQL)                                           │
│  SELECT * FROM PRODUCT p                                    │
│  JOIN MERCHANT_STORE ms ON p.MERCHANT_ID = ms.ID            │
│  WHERE p.ID = 123 AND ms.CODE = 'DEFAULT'                   │
└─────────────────────────────────────────────────────────────┘
     │
     │ 7. Return Product Entity
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductService                                             │
│  - Return Product entity                                    │
└─────────────────────────────────────────────────────────────┘
     │
     │ 8. Convert to DTO
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ReadableProductMapper                                      │
│  - Map Product → ReadableProduct                            │
│  - Add image URLs                                           │
│  - Format prices                                            │
│  - Add descriptions in requested language                   │
└─────────────────────────────────────────────────────────────┘
     │
     │ 9. Return DTO
     ▼
┌─────────────────────────────────────────────────────────────┐
│  ProductApi (Controller)                                    │
│  - Wrap in ResponseEntity                                   │
│  - Set HTTP status: 200 OK                                  │
│  - Serialize to JSON                                        │
└─────────────────────────────────────────────────────────────┘
     │
     │ 10. HTTP Response
     ▼
┌──────────┐
│  Client  │ Receives ReadableProduct JSON
└──────────┘
```

## Multi-Store Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                      SINGLE SHOPIZER INSTANCE                       │
└─────────────────────────────────────────────────────────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
                ▼               ▼               ▼
        ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
        │   Store A    │ │   Store B    │ │   Store C    │
        │  (DEFAULT)   │ │  (FASHION)   │ │  (TECH)      │
        │              │ │              │ │              │
        │ domain:      │ │ domain:      │ │ domain:      │
        │ shop-a.com   │ │ shop-b.com   │ │ shop-c.com   │
        └──────────────┘ └──────────────┘ └──────────────┘
                │               │               │
                └───────────────┼───────────────┘
                                │
                                ▼
        ┌───────────────────────────────────────────────┐
        │         SHARED DATABASE                       │
        │                                               │
        │  ┌─────────────────────────────────────────┐ │
        │  │  MERCHANT_STORE                         │ │
        │  │  - id: 1, code: DEFAULT                 │ │
        │  │  - id: 2, code: FASHION                 │ │
        │  │  - id: 3, code: TECH                    │ │
        │  └─────────────────────────────────────────┘ │
        │                                               │
        │  ┌─────────────────────────────────────────┐ │
        │  │  PRODUCT                                │ │
        │  │  - id: 1, merchant_id: 1 (Store A)      │ │
        │  │  - id: 2, merchant_id: 1 (Store A)      │ │
        │  │  - id: 3, merchant_id: 2 (Store B)      │ │
        │  │  - id: 4, merchant_id: 3 (Store C)      │ │
        │  └─────────────────────────────────────────┘ │
        │                                               │
        │  All entities filtered by merchant_id        │
        └───────────────────────────────────────────────┘
```

## Security Flow

```
┌──────────────────────────────────────────────────────────────┐
│  1. Login Request                                            │
│  POST /api/v1/auth/login                                     │
│  { "username": "user@example.com", "password": "pass123" }   │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  2. AuthenticationController                                 │
│  - Validate credentials                                      │
│  - Load user from database                                   │
│  - Check password (BCrypt)                                   │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  3. JWTTokenUtil                                             │
│  - Generate JWT token                                        │
│  - Include: username, roles, expiration                      │
│  - Sign with secret key                                      │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  4. Return Token                                             │
│  { "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }      │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  5. Subsequent Requests                                      │
│  GET /api/v1/products                                        │
│  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9  │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  6. AuthenticationTokenFilter                                │
│  - Extract token from header                                 │
│  - Validate token signature                                  │
│  - Check expiration                                          │
│  - Extract username                                          │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  7. Load User Details                                        │
│  - Query database for user                                   │
│  - Load roles and permissions                                │
│  - Create Authentication object                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  8. Set SecurityContext                                      │
│  - Store authentication in context                           │
│  - Available throughout request                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│  9. Authorization Check                                      │
│  @PreAuthorize("hasRole('ADMIN')")                           │
│  - Check user has required role                              │
│  - Allow or deny access                                      │
└──────────────────────────────────────────────────────────────┘
```

## Module Dependencies

```
                    ┌──────────────┐
                    │   sm-shop    │
                    │  (Main App)  │
                    └──────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│  sm-core     │   │ sm-shop-model│   │ sm-core-model│
│              │   │              │   │              │
│ (Business    │   │ (API DTOs)   │   │ (Entities)   │
│  Logic)      │   │              │   │              │
└──────────────┘   └──────────────┘   └──────────────┘
        │                                      ▲
        │                                      │
        └──────────────┬───────────────────────┘
                       │
        ┌──────────────┴──────────────┐
        │                             │
        ▼                             ▼
┌──────────────┐            ┌──────────────┐
│sm-core-modules│            │ sm-core-model│
│              │            │              │
│(Integrations)│            │  (Entities)  │
└──────────────┘            └──────────────┘
```

## Technology Stack Layers

```
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                        │
│  Spring Boot 2.5.12 │ Spring MVC │ Spring Security          │
│  Swagger 2.9.2      │ JWT Auth   │ Bean Validation          │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                   Business Logic Layer                      │
│  Spring Data JPA │ Drools 7.32 │ Spring Cache               │
│  MapStruct       │ Guava       │ Commons Utils              │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                   Persistence Layer                         │
│  Hibernate 5.x │ HikariCP │ Ehcache                         │
│  MySQL / PostgreSQL / H2 / Oracle                           │
└─────────────────────────────────────────────────────────────┘
                            │
┌─────────────────────────────────────────────────────────────┐
│                   External Services                         │
│  Elasticsearch 7.5.2 │ Payment Gateways │ SMTP              │
│  AWS S3 / GCS        │ Shipping APIs    │ GeoIP2            │
└─────────────────────────────────────────────────────────────┘
```

This visual overview should help you understand how all the pieces of Shopizer fit together!
