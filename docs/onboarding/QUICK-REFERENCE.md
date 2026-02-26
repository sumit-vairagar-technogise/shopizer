# Quick Reference Guide

A cheat sheet for common tasks and patterns in Shopizer.

## 🚀 Quick Commands

### Build & Run
```bash
# Build entire project
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run application
cd sm-shop && mvn spring-boot:run

# Run with MySQL profile
mvn spring-boot:run -Dspring-boot.run.profiles=mysql

# Run tests
mvn test

# Package for deployment
mvn clean package -DskipTests
```

### Docker
```bash
# Build image
docker build -t shopizer .

# Run container
docker run -p 8080:8080 shopizer

# Run with environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=mysql \
  -e DB_HOST=mysql-host \
  shopizer
```

## 📁 Key File Locations

| What | Where |
|------|-------|
| Main Application | `sm-shop/src/main/java/com/salesmanager/shop/application/ShopApplication.java` |
| REST Controllers | `sm-shop/src/main/java/com/salesmanager/shop/store/api/v1/` |
| Services | `sm-core/src/main/java/com/salesmanager/core/business/services/` |
| Repositories | `sm-core/src/main/java/com/salesmanager/core/business/repositories/` |
| Entities | `sm-core-model/src/main/java/com/salesmanager/core/model/` |
| DTOs | `sm-shop-model/src/main/java/com/salesmanager/shop/model/` |
| Security Config | `sm-shop/src/main/java/com/salesmanager/shop/application/config/MultipleEntryPointsSecurityConfig.java` |
| Application Config | `sm-shop/src/main/resources/application.properties` |
| Database Config | `sm-shop/src/main/resources/profiles/[profile]/database.properties` |

## 🔌 Common API Endpoints

### Store
```bash
# Get store info
GET /api/v1/store/{code}

# List all stores
GET /api/v1/stores
```

### Products
```bash
# List products
GET /api/v1/products?store=DEFAULT&lang=en&page=0&count=20

# Get product by ID
GET /api/v1/products/{id}?store=DEFAULT&lang=en

# Create product
POST /api/v1/private/products
Authorization: Bearer {token}

# Update product
PUT /api/v1/private/products/{id}
Authorization: Bearer {token}

# Delete product
DELETE /api/v1/private/products/{id}
Authorization: Bearer {token}
```

### Categories
```bash
# List categories
GET /api/v1/category?store=DEFAULT&lang=en

# Get category by code
GET /api/v1/category/{code}?store=DEFAULT&lang=en

# Create category
POST /api/v1/private/category
Authorization: Bearer {token}
```

### Customers
```bash
# Register customer
POST /api/v1/customer/register

# Login customer
POST /api/v1/customer/login

# Get customer profile
GET /api/v1/customer/{id}
Authorization: Bearer {token}

# Update customer
PUT /api/v1/customer/{id}
Authorization: Bearer {token}
```

### Shopping Cart
```bash
# Get cart
GET /api/v1/cart/{code}

# Add to cart
POST /api/v1/cart

# Update cart item
PUT /api/v1/cart/{code}

# Remove from cart
DELETE /api/v1/cart/{code}/product/{productId}
```

### Orders
```bash
# Create order
POST /api/v1/order

# Get order
GET /api/v1/order/{id}
Authorization: Bearer {token}

# List customer orders
GET /api/v1/customer/{customerId}/orders
Authorization: Bearer {token}
```

### Authentication
```bash
# Customer login
POST /api/v1/customer/login
{
  "username": "customer@example.com",
  "password": "password"
}

# Admin login
POST /api/v1/private/login
{
  "username": "admin@shopizer.com",
  "password": "password"
}
```

## 🏗️ Code Patterns

### Creating a REST Controller

```java
@Controller
@RequestMapping("/api/v1")
@Api(tags = {"My API"})
public class MyApi {
    
    @Inject
    private MyFacade myFacade;
    
    @GetMapping("/myresource/{id}")
    @ApiOperation(value = "Get resource by ID")
    public ResponseEntity<ReadableResource> getResource(
        @PathVariable Long id,
        @ApiIgnore MerchantStore store,
        @ApiIgnore Language language
    ) {
        ReadableResource resource = myFacade.getResource(id, store, language);
        return ResponseEntity.ok(resource);
    }
    
    @PostMapping("/private/myresource")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReadableResource> createResource(
        @Valid @RequestBody PersistableResource resource,
        @ApiIgnore MerchantStore store
    ) {
        ReadableResource created = myFacade.createResource(resource, store);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

### Creating a Facade

```java
@Service
public class MyFacadeImpl implements MyFacade {
    
    @Inject
    private MyService myService;
    
    @Inject
    private ReadableResourceMapper mapper;
    
    @Override
    public ReadableResource getResource(Long id, MerchantStore store, Language language) {
        MyEntity entity = myService.getById(id, store);
        if (entity == null) {
            throw new ResourceNotFoundException("Resource not found");
        }
        return mapper.convert(entity, store, language);
    }
    
    @Override
    public ReadableResource createResource(PersistableResource resource, MerchantStore store) {
        MyEntity entity = new MyEntity();
        entity.setMerchantStore(store);
        // Set other properties
        
        myService.save(entity);
        return mapper.convert(entity, store, store.getDefaultLanguage());
    }
}
```

### Creating a Service

```java
@Service
public class MyServiceImpl implements MyService {
    
    @Inject
    private MyRepository myRepository;
    
    @Override
    @Transactional(readOnly = true)
    public MyEntity getById(Long id, MerchantStore store) {
        return myRepository.findByIdAndStore(id, store.getId())
            .orElseThrow(() -> new ServiceException("Entity not found"));
    }
    
    @Override
    @Transactional
    public MyEntity save(MyEntity entity) {
        Validate.notNull(entity, "Entity cannot be null");
        return myRepository.save(entity);
    }
    
    @Override
    @Transactional
    public void delete(MyEntity entity) {
        myRepository.delete(entity);
    }
}
```

### Creating a Repository

```java
public interface MyRepository extends JpaRepository<MyEntity, Long> {
    
    Optional<MyEntity> findByIdAndStore(Long id, Long storeId);
    
    @Query("SELECT e FROM MyEntity e WHERE e.merchantStore.id = :storeId AND e.active = true")
    List<MyEntity> findActiveByStore(@Param("storeId") Long storeId);
    
    @Query("SELECT e FROM MyEntity e WHERE e.code = :code AND e.merchantStore.id = :storeId")
    Optional<MyEntity> findByCodeAndStore(@Param("code") String code, @Param("storeId") Long storeId);
}
```

### Creating an Entity

```java
@Entity
@Table(name = "MY_ENTITY", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class MyEntity extends SalesManagerEntity<Long, MyEntity> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_ENTITY_ID")
    private Long id;
    
    @Column(name = "CODE", unique = true, nullable = false)
    private String code;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    private MerchantStore merchantStore;
    
    @Column(name = "ACTIVE")
    private boolean active = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED")
    private Date dateCreated;
    
    // Getters and setters
}
```

### Creating DTOs

```java
// Request DTO
public class PersistableResource extends ResourceEntity {
    private String code;
    private String name;
    private boolean active;
    
    // Getters and setters
}

// Response DTO
public class ReadableResource extends ResourceEntity {
    private Long id;
    private String code;
    private String name;
    private boolean active;
    private String createdDate;
    
    // Getters and setters
}
```

### Creating a Mapper

```java
@Component
public class ReadableResourceMapper implements Mapper<MyEntity, ReadableResource> {
    
    @Override
    public ReadableResource convert(MyEntity source, MerchantStore store, Language language) {
        ReadableResource target = new ReadableResource();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setActive(source.isActive());
        
        if (source.getDateCreated() != null) {
            target.setCreatedDate(DateUtil.formatDate(source.getDateCreated()));
        }
        
        return target;
    }
}
```

## 🔐 Security Patterns

### Securing an Endpoint

```java
// Require authentication
@PreAuthorize("isAuthenticated()")
public ResponseEntity<Resource> getResource() { }

// Require specific role
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Resource> adminOnly() { }

// Require any of multiple roles
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
public ResponseEntity<Resource> adminOrSuper() { }

// Custom permission check
@PreAuthorize("hasPermission(#id, 'RESOURCE', 'READ')")
public ResponseEntity<Resource> getResource(@PathVariable Long id) { }
```

### Getting Current User

```java
@Inject
private UserService userService;

// In controller or service
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName();
User user = userService.getByUserName(username);
```

## 🗄️ Database Patterns

### Pagination

```java
// In repository
Page<MyEntity> findByStore(Long storeId, Pageable pageable);

// In service
public Page<MyEntity> list(MerchantStore store, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
    return myRepository.findByStore(store.getId(), pageable);
}
```

### Criteria Queries

```java
public class MyCriteria extends Criteria {
    private String code;
    private Boolean active;
    private Long storeId;
    
    // Getters and setters
}

// In repository
@Query("SELECT e FROM MyEntity e WHERE " +
       "(:code IS NULL OR e.code = :code) AND " +
       "(:active IS NULL OR e.active = :active) AND " +
       "e.merchantStore.id = :storeId")
List<MyEntity> findByCriteria(
    @Param("code") String code,
    @Param("active") Boolean active,
    @Param("storeId") Long storeId
);
```

## 🧪 Testing Patterns

### Unit Test

```java
@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {
    
    @Mock
    private MyRepository myRepository;
    
    @InjectMocks
    private MyServiceImpl myService;
    
    @Test
    public void testGetById() {
        // Given
        MyEntity entity = new MyEntity();
        entity.setId(1L);
        when(myRepository.findById(1L)).thenReturn(Optional.of(entity));
        
        // When
        MyEntity result = myService.getById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        verify(myRepository).findById(1L);
    }
}
```

### Integration Test

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MyServiceIntegrationTest {
    
    @Inject
    private MyService myService;
    
    @Inject
    private MerchantStoreService merchantStoreService;
    
    @Test
    public void testSaveAndRetrieve() {
        // Given
        MerchantStore store = merchantStoreService.getByCode("DEFAULT");
        MyEntity entity = new MyEntity();
        entity.setMerchantStore(store);
        entity.setCode("TEST");
        
        // When
        myService.save(entity);
        MyEntity retrieved = myService.getByCode("TEST", store);
        
        // Then
        assertNotNull(retrieved);
        assertEquals("TEST", retrieved.getCode());
    }
}
```

## 📝 Configuration Examples

### application.properties

```properties
# Server
server.port=8080

# Database
spring.jpa.properties.hibernate.default_schema=SALESMANAGER
hibernate.hbm2ddl.auto=update

# Logging
logging.level.com.shopizer=INFO
logging.level.org.hibernate.SQL=DEBUG

# File upload
spring.servlet.multipart.max-file-size=4MB
spring.servlet.multipart.max-request-size=10MB

# Actuator
management.endpoints.web.exposure.include=health,info
```

### database.properties (MySQL)

```properties
db.jdbcUrl=jdbc:mysql://localhost:3306/SALESMANAGER?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8
db.user=shopizer
db.password=your-password
db.driverClass=com.mysql.cj.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
db.show.sql=false
db.schema=SALESMANAGER
hibernate.hbm2ddl.auto=update
```

## 🐛 Debugging Tips

### Enable SQL Logging
```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Enable Request Logging
```properties
logging.level.org.springframework.web=DEBUG
```

### Enable Security Logging
```properties
logging.level.org.springframework.security=DEBUG
```

### Common Issues

**Port already in use**:
```bash
# Find process using port 8080
lsof -i :8080
# Kill process
kill -9 <PID>
```

**Database connection failed**:
- Check database is running
- Verify credentials in database.properties
- Check firewall settings

**JWT token expired**:
- Login again to get new token
- Increase token expiration time in config

## 📚 Useful Resources

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console (if enabled)
- **Actuator Health**: http://localhost:8080/actuator/health
- **Actuator Info**: http://localhost:8080/actuator/info

## 🎯 Next Steps

- Explore [API Guide](06-api-guide.md) for detailed API documentation
- Review [Database Schema](07-database-schema.md) for data model
- Check [Development Workflow](08-development-workflow.md) for best practices
