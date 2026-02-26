# Project Structure

## Maven Multi-Module Structure

```
shopizer/
├── pom.xml                    # Parent POM
├── sm-core-model/             # Domain entities (JPA models)
├── sm-core-modules/           # Integration modules
├── sm-core/                   # Business logic
├── sm-shop-model/             # API DTOs
└── sm-shop/                   # REST API application (main)
```

## Module Dependencies

```
┌─────────────────────────────────────────────────────────────┐
│                        sm-shop                              │
│                   (Main Application)                        │
│  - REST Controllers                                         │
│  - Security Configuration                                   │
│  - Swagger Setup                                            │
└─────────────────────────────────────────────────────────────┘
         │                    │                    │
         │ depends on         │ depends on         │ depends on
         ▼                    ▼                    ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│  sm-core     │    │ sm-shop-model│    │ sm-core-model│
│              │    │              │    │              │
│ (Business    │    │ (API DTOs)   │    │ (Entities)   │
│  Logic)      │    │              │    │              │
└──────────────┘    └──────────────┘    └──────────────┘
         │                                       ▲
         │ depends on                            │
         ▼                                       │
┌──────────────┐    ┌──────────────┐           │
│sm-core-modules│    │ sm-core-model│───────────┘
│              │    │              │
│(Integrations)│    │  (Entities)  │
└──────────────┘    └──────────────┘
```

## 1. sm-core-model (Domain Entities)

**Purpose**: JPA entities and domain models

```
sm-core-model/
└── src/main/java/com/salesmanager/core/
    ├── model/
    │   ├── catalog/
    │   │   ├── product/
    │   │   │   ├── Product.java
    │   │   │   ├── ProductDescription.java
    │   │   │   ├── ProductImage.java
    │   │   │   ├── ProductPrice.java
    │   │   │   ├── ProductAttribute.java
    │   │   │   └── ...
    │   │   ├── category/
    │   │   │   ├── Category.java
    │   │   │   └── CategoryDescription.java
    │   │   └── catalog/
    │   │       └── Catalog.java
    │   │
    │   ├── customer/
    │   │   ├── Customer.java
    │   │   ├── CustomerAttribute.java
    │   │   └── review/
    │   │       └── CustomerReview.java
    │   │
    │   ├── order/
    │   │   ├── Order.java
    │   │   ├── OrderTotal.java
    │   │   ├── orderproduct/
    │   │   │   └── OrderProduct.java
    │   │   └── orderstatus/
    │   │       └── OrderStatus.java
    │   │
    │   ├── merchant/
    │   │   └── MerchantStore.java
    │   │
    │   ├── user/
    │   │   ├── User.java
    │   │   ├── Group.java
    │   │   └── Permission.java
    │   │
    │   ├── shoppingcart/
    │   │   ├── ShoppingCart.java
    │   │   └── ShoppingCartItem.java
    │   │
    │   ├── shipping/
    │   ├── payments/
    │   ├── tax/
    │   ├── content/
    │   ├── reference/
    │   │   ├── country/
    │   │   ├── zone/
    │   │   ├── language/
    │   │   └── currency/
    │   └── system/
    │
    └── business/exception/
        └── ServiceException.java
```

**Key Entities**:
- `Product` - Product catalog
- `Category` - Product categories
- `Customer` - End users
- `Order` - Customer orders
- `MerchantStore` - Store/tenant
- `User` - Admin users

## 2. sm-core-modules (Integration Modules)

**Purpose**: External integrations (payment, shipping, email, CMS)

```
sm-core-modules/
└── src/main/java/com/salesmanager/core/modules/
    ├── integration/
    │   ├── payment/
    │   │   ├── PaymentModule.java
    │   │   └── impl/
    │   │       ├── PayPalPayment.java
    │   │       └── StripePayment.java
    │   │
    │   └── shipping/
    │       ├── ShippingModule.java
    │       └── impl/
    │           └── CanadaPostShipping.java
    │
    ├── email/
    │   ├── EmailModule.java
    │   └── impl/
    │       └── SmtpEmailSender.java
    │
    └── cms/
        ├── FileManager.java
        └── impl/
            ├── LocalFileManager.java
            ├── S3FileManager.java
            └── GCSFileManager.java
```

## 3. sm-core (Business Logic)

**Purpose**: Services, repositories, and business logic

```
sm-core/
└── src/main/java/com/salesmanager/core/business/
    ├── services/
    │   ├── catalog/
    │   │   ├── product/
    │   │   │   ├── ProductService.java
    │   │   │   ├── ProductServiceImpl.java
    │   │   │   ├── PricingService.java
    │   │   │   └── ProductImageService.java
    │   │   │
    │   │   ├── category/
    │   │   │   ├── CategoryService.java
    │   │   │   └── CategoryServiceImpl.java
    │   │   │
    │   │   └── pricing/
    │   │
    │   ├── customer/
    │   │   ├── CustomerService.java
    │   │   ├── CustomerServiceImpl.java
    │   │   └── CustomerReviewService.java
    │   │
    │   ├── order/
    │   │   ├── OrderService.java
    │   │   ├── OrderServiceImpl.java
    │   │   └── OrderTotalService.java
    │   │
    │   ├── shoppingcart/
    │   │   ├── ShoppingCartService.java
    │   │   └── ShoppingCartServiceImpl.java
    │   │
    │   ├── merchant/
    │   │   └── MerchantStoreService.java
    │   │
    │   ├── user/
    │   │   ├── UserService.java
    │   │   └── PermissionService.java
    │   │
    │   ├── shipping/
    │   ├── payments/
    │   ├── tax/
    │   ├── content/
    │   ├── reference/
    │   └── search/
    │
    ├── repositories/
    │   ├── catalog/
    │   │   ├── product/
    │   │   │   ├── ProductRepository.java
    │   │   │   └── ProductImageRepository.java
    │   │   └── category/
    │   │       └── CategoryRepository.java
    │   │
    │   ├── customer/
    │   │   └── CustomerRepository.java
    │   │
    │   ├── order/
    │   │   └── OrderRepository.java
    │   │
    │   ├── merchant/
    │   │   └── MerchantStoreRepository.java
    │   │
    │   └── user/
    │       └── UserRepository.java
    │
    ├── modules/
    │   ├── order/
    │   ├── email/
    │   └── cms/
    │
    └── configuration/
        ├── DataConfiguration.java
        └── DroolsBeanFactory.java
```

## 4. sm-shop-model (API DTOs)

**Purpose**: Request/Response models for REST APIs

```
sm-shop-model/
└── src/main/java/com/salesmanager/shop/model/
    ├── catalog/
    │   ├── product/
    │   │   ├── ReadableProduct.java          # Response DTO
    │   │   ├── PersistableProduct.java       # Request DTO
    │   │   ├── LightPersistableProduct.java  # Minimal request
    │   │   ├── ReadableProductList.java      # List response
    │   │   ├── ReadableProductPrice.java
    │   │   └── ProductSpecification.java
    │   │
    │   └── category/
    │       ├── ReadableCategory.java
    │       └── PersistableCategory.java
    │
    ├── customer/
    │   ├── ReadableCustomer.java
    │   ├── PersistableCustomer.java
    │   └── CustomerEntity.java
    │
    ├── order/
    │   ├── ReadableOrder.java
    │   ├── PersistableOrder.java
    │   └── ReadableOrderList.java
    │
    ├── cart/
    │   ├── ReadableShoppingCart.java
    │   └── PersistableShoppingCart.java
    │
    ├── merchant/
    │   ├── ReadableMerchantStore.java
    │   └── PersistableMerchantStore.java
    │
    └── entity/
        ├── Entity.java
        ├── EntityExists.java
        └── ReadableList.java
```

**Naming Convention**:
- `Readable*` - Response DTOs (what API returns)
- `Persistable*` - Request DTOs (what API accepts)
- `Light*` - Minimal/simplified versions

## 5. sm-shop (Main Application)

**Purpose**: REST API, security, configuration

```
sm-shop/
├── src/main/java/com/salesmanager/shop/
│   ├── application/
│   │   ├── ShopApplication.java           # Main class
│   │   └── config/
│   │       ├── MultipleEntryPointsSecurityConfig.java
│   │       ├── DocumentationConfiguration.java
│   │       ├── ShopApplicationConfiguration.java
│   │       └── WebConfig.java
│   │
│   ├── store/
│   │   ├── api/
│   │   │   ├── v1/                        # API Version 1
│   │   │   │   ├── product/
│   │   │   │   │   ├── ProductApi.java
│   │   │   │   │   ├── ProductImageApi.java
│   │   │   │   │   ├── ProductPriceApi.java
│   │   │   │   │   └── ProductReviewApi.java
│   │   │   │   │
│   │   │   │   ├── category/
│   │   │   │   │   └── CategoryApi.java
│   │   │   │   │
│   │   │   │   ├── customer/
│   │   │   │   │   ├── CustomerApi.java
│   │   │   │   │   └── AuthenticateCustomerApi.java
│   │   │   │   │
│   │   │   │   ├── order/
│   │   │   │   │   ├── OrderApi.java
│   │   │   │   │   └── OrderPaymentApi.java
│   │   │   │   │
│   │   │   │   ├── shoppingCart/
│   │   │   │   │   └── ShoppingCartApi.java
│   │   │   │   │
│   │   │   │   ├── user/
│   │   │   │   │   ├── UserApi.java
│   │   │   │   │   └── AuthenticateUserApi.java
│   │   │   │   │
│   │   │   │   ├── store/
│   │   │   │   │   └── MerchantStoreApi.java
│   │   │   │   │
│   │   │   │   ├── shipping/
│   │   │   │   ├── payment/
│   │   │   │   ├── tax/
│   │   │   │   ├── content/
│   │   │   │   ├── search/
│   │   │   │   └── system/
│   │   │   │
│   │   │   └── v2/                        # API Version 2
│   │   │
│   │   ├── facade/                        # Service facades
│   │   │   ├── product/
│   │   │   │   ├── ProductFacade.java
│   │   │   │   └── ProductFacadeImpl.java
│   │   │   ├── category/
│   │   │   ├── customer/
│   │   │   ├── order/
│   │   │   └── shoppingCart/
│   │   │
│   │   └── security/
│   │       ├── JWTTokenUtil.java
│   │       ├── AuthenticationTokenFilter.java
│   │       └── customer/
│   │           └── CustomerServicesImpl.java
│   │
│   ├── mapper/                            # Entity ↔ DTO mappers
│   │   ├── catalog/
│   │   │   ├── ReadableProductMapper.java
│   │   │   └── PersistableProductMapper.java
│   │   ├── customer/
│   │   ├── order/
│   │   └── cart/
│   │
│   ├── populator/                         # Legacy populators
│   │   ├── catalog/
│   │   ├── customer/
│   │   └── order/
│   │
│   ├── utils/
│   │   ├── ImageFilePath.java
│   │   ├── EmailUtils.java
│   │   └── LanguageUtils.java
│   │
│   └── constants/
│       └── Constants.java
│
└── src/main/resources/
    ├── application.properties             # Main config
    ├── profiles/                          # Environment configs
    │   ├── local/
    │   │   └── database.properties
    │   ├── mysql/
    │   │   └── database.properties
    │   ├── docker/
    │   └── cloud/
    │
    ├── spring/
    │   └── shopizer-servlet-context.xml
    │
    ├── bundles/                           # i18n messages
    │   ├── messages.properties
    │   ├── messages_fr.properties
    │   └── shopizer.properties
    │
    └── static/
        └── favicon.ico
```

## Configuration Files

### Root Level
```
shopizer/
├── pom.xml                    # Parent POM with dependency management
├── mvnw                       # Maven wrapper (Unix)
├── mvnw.cmd                   # Maven wrapper (Windows)
├── .gitignore
├── README.md
├── LICENSE.md
└── .circleci/
    └── config.yml             # CI/CD configuration
```

### Application Configuration
```
sm-shop/src/main/resources/
├── application.properties     # Spring Boot config
├── shopizer-properties.properties
├── profiles/
│   ├── local/database.properties
│   ├── mysql/database.properties
│   ├── docker/database.properties
│   ├── gcp/database.properties
│   └── aws/database.properties
```

## Package Naming Convention

```
com.salesmanager
├── core                       # Core business logic
│   ├── business
│   │   ├── services          # Business services
│   │   ├── repositories      # Data access
│   │   ├── modules           # Integration modules
│   │   └── configuration     # Core config
│   └── model                 # Domain entities
│
└── shop                      # API layer
    ├── application           # Spring Boot app
    ├── store
    │   ├── api               # REST controllers
    │   ├── facade            # Service facades
    │   └── security          # Security
    ├── mapper                # DTO mappers
    ├── populator             # Legacy populators
    ├── model                 # API DTOs
    └── utils                 # Utilities
```

## Key Files to Know

| File | Purpose |
|------|---------|
| `ShopApplication.java` | Spring Boot main class |
| `MultipleEntryPointsSecurityConfig.java` | Security configuration |
| `DocumentationConfiguration.java` | Swagger setup |
| `application.properties` | Main application config |
| `database.properties` | Database connection config |
| `pom.xml` (root) | Parent POM with versions |

## Build Output

```
target/
├── classes/                   # Compiled classes
├── generated-sources/         # Generated code
├── sm-shop-3.2.5.jar         # Executable JAR
└── test-classes/             # Test classes
```

## Next Steps

Continue to [Getting Started](05-getting-started.md) to set up your development environment.
