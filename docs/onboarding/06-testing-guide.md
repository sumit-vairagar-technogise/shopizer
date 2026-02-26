# Testing Guide

## Overview

Shopizer has two types of tests:
1. **Unit Tests** - Test individual components in isolation
2. **Integration Tests** - Test components working together with database and services

## Test Structure

```
shopizer/
├── sm-core/
│   └── src/test/java/
│       └── com/salesmanager/test/
│           ├── catalog/          # Product, Category tests
│           ├── customer/         # Customer tests
│           ├── order/            # Order tests
│           ├── shoppingcart/     # Cart tests
│           ├── shipping/         # Shipping tests
│           ├── content/          # CMS/File tests
│           ├── configuration/    # Config tests
│           ├── references/       # Reference data tests
│           └── common/           # Test base classes
│
└── sm-shop/
    └── src/test/java/
        └── com/salesmanager/test/shop/
            └── util/             # Utility tests
```

## Running Tests

### Run All Tests
```bash
# From project root
mvn test

# Specific module
cd sm-core && mvn test
cd sm-shop && mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=CategoryTest
mvn test -Dtest=CustomerTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=CategoryTest#testCreateCategory
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

## Test Categories

### ✅ Active Integration Tests (sm-core)

These tests run automatically and verify core business logic:

| Test Class | What It Tests | Status |
|------------|---------------|--------|
| `CategoryTest` | Category CRUD operations | ✅ Passing |
| `ManufacturerTest` | Manufacturer management | ✅ Passing |
| `ProductNextGenTest` | Product variants | ✅ Passing |
| `CustomerTest` | Customer operations | ✅ Passing |
| `ShoppingCartTest` | Cart functionality | ✅ Passing |
| `ConfigurationTest` | System configuration | ✅ Passing |
| `DataUtilsTest` | Utility functions | ✅ Passing |

**Total**: 28 tests, 14 passing, 14 skipped

### ⏭️ Skipped Integration Tests

These tests are marked with `@Ignore` because they require external services:

| Test Class | Why Skipped | Requires |
|------------|-------------|----------|
| `ProductTest` | Image file storage | CMS configuration |
| `ContentImagesTest` | Image operations | CMS/File storage |
| `ContentFolderTest` | Folder operations | CMS/File storage |
| `StaticContentTest` | Static content | CMS/File storage |
| `SendEmailTest` | Email sending | SMTP server |
| `OrderTest` | Order processing | Payment gateway |
| `InvoiceTest` | Invoice generation | PDF generation |
| `ShippingMethodDecisionTest` | Shipping rules | Drools config |
| `ShippingQuoteByWeightTest` | Shipping quotes | Shipping provider |
| `ReferencesTest` | Reference data | External data |
| `UtilsTestCase` | Utility tests | Various |

## Test Base Classes

### AbstractSalesManagerCoreTestCase

Base class for all integration tests in `sm-core`:

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring/shopizer-core-context.xml",
    "classpath:spring/shopizer-core-modules.xml"
})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class
})
@Transactional
public abstract class AbstractSalesManagerCoreTestCase {
    
    @Inject
    protected ProductService productService;
    
    @Inject
    protected CategoryService categoryService;
    
    @Inject
    protected CustomerService customerService;
    
    // ... other services
}
```

**Features**:
- Spring context loaded
- All services auto-injected
- Transactional (rollback after each test)
- H2 in-memory database

## What Integration Tests Verify

### 1. Catalog Tests (`catalog/`)

**CategoryTest**:
- ✅ Create category with descriptions
- ✅ Update category
- ✅ Delete category
- ✅ Category hierarchy
- ✅ Multi-language support

**ManufacturerTest**:
- ✅ Create manufacturer
- ✅ Update manufacturer
- ✅ Associate with products

**ProductNextGenTest**:
- ✅ Product variants
- ✅ Product options
- ✅ Product attributes

**ProductTest** (Skipped):
- ⏭️ Product CRUD
- ⏭️ Product images
- ⏭️ Product pricing
- ⏭️ Product relationships

### 2. Customer Tests (`customer/`)

**CustomerTest**:
- ✅ Create customer
- ✅ Update customer
- ✅ Customer addresses
- ✅ Customer authentication
- ✅ Customer reviews

### 3. Shopping Cart Tests (`shoppingcart/`)

**ShoppingCartTest**:
- ✅ Add items to cart
- ✅ Update cart quantities
- ✅ Remove items
- ✅ Calculate totals
- ✅ Apply discounts

### 4. Order Tests (`order/`)

**OrderTest** (Skipped):
- ⏭️ Create order
- ⏭️ Order totals
- ⏭️ Order status
- ⏭️ Payment processing

**InvoiceTest** (Skipped):
- ⏭️ Generate invoice
- ⏭️ Invoice PDF

### 5. Configuration Tests (`configuration/`)

**ConfigurationTest**:
- ✅ System configuration
- ✅ Merchant store config
- ✅ Module configuration

### 6. Content Tests (`content/`)

All skipped - require CMS setup:
- ⏭️ Image upload/download
- ⏭️ Folder operations
- ⏭️ Static content

### 7. Shipping Tests (`shipping/`)

All skipped - require shipping providers:
- ⏭️ Shipping quotes
- ⏭️ Shipping methods
- ⏭️ Distance calculations

## Test Configuration

### Test Database

Tests use **H2 in-memory database**:

**Location**: `sm-core/src/test/resources/database.properties`

```properties
db.jdbcUrl=jdbc:h2:mem:SALESMANAGER
db.user=sa
db.password=
db.driverClass=org.h2.Driver
hibernate.dialect=org.hibernate.dialect.H2Dialect
hibernate.hbm2ddl.auto=create-drop
```

**Features**:
- Fresh database for each test run
- Fast (in-memory)
- No external dependencies
- Auto-creates schema

### Test Properties

**Location**: `sm-core/src/test/resources/shopizer-core.properties`

Key settings:
```properties
# Disable Elasticsearch indexing in tests
search.noindex=true

# Use default (Infinispan) CMS
config.cms.method=default

# File storage locations
config.cms.store.location=./files/store
config.cms.files.location=./files/repos
```

## Writing New Tests

### Integration Test Template

```java
package com.salesmanager.test.mymodule;

import org.junit.Assert;
import org.junit.Test;
import com.salesmanager.test.common.AbstractSalesManagerCoreTestCase;

public class MyModuleTest extends AbstractSalesManagerCoreTestCase {
    
    @Test
    public void testMyFeature() throws Exception {
        // Given - Setup test data
        MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
        Language en = languageService.getByCode("en");
        
        // When - Execute the operation
        MyEntity entity = new MyEntity();
        entity.setMerchantStore(store);
        myService.save(entity);
        
        // Then - Verify results
        MyEntity retrieved = myService.getById(entity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(entity.getId(), retrieved.getId());
    }
}
```

### Unit Test Template

```java
package com.salesmanager.test.utils;

import org.junit.Assert;
import org.junit.Test;

public class MyUtilTest {
    
    @Test
    public void testUtilityMethod() {
        // Given
        String input = "test";
        
        // When
        String result = MyUtil.process(input);
        
        // Then
        Assert.assertEquals("expected", result);
    }
}
```

## Test Best Practices

### ✅ Do's

1. **Use descriptive test names**
   ```java
   @Test
   public void testCreateCategoryWithMultipleLanguages()
   ```

2. **Follow Given-When-Then pattern**
   ```java
   // Given - setup
   // When - execute
   // Then - verify
   ```

3. **Clean up test data**
   ```java
   @After
   public void cleanup() {
       // Delete test data if needed
   }
   ```

4. **Test one thing per test**
   - Each test should verify one specific behavior

5. **Use meaningful assertions**
   ```java
   Assert.assertNotNull("Product should not be null", product);
   ```

### ❌ Don'ts

1. **Don't depend on test execution order**
   - Tests should be independent

2. **Don't use hardcoded IDs**
   - Use generated IDs or constants

3. **Don't test external services directly**
   - Mock external dependencies

4. **Don't ignore test failures**
   - Fix or document why skipped

## Troubleshooting Tests

### Test Fails with "Table not found"

**Cause**: Database schema not created

**Solution**: Check `hibernate.hbm2ddl.auto=create-drop` in test config

### Test Fails with "Bean not found"

**Cause**: Spring context not loaded properly

**Solution**: Check `@ContextConfiguration` paths

### Test Fails with Transaction Rollback

**Cause**: Exception in nested transaction

**Solution**: Check logs for actual exception, fix the root cause

### Tests Run Slowly

**Cause**: Database operations or external calls

**Solution**:
- Use in-memory H2 (already configured)
- Mock external services
- Reduce test data size

## Test Coverage

Current coverage (sm-core):
- **Lines**: ~30%
- **Branches**: ~37%

**Goal**: Increase to 60%+ for critical business logic

### Measuring Coverage

```bash
# Run tests with coverage
mvn clean test jacoco:report

# View report
open sm-core/target/site/jacoco/index.html
```

## Continuous Integration

Tests run automatically on:
- Every commit (CircleCI)
- Pull requests
- Before deployment

**CircleCI Config**: `.circleci/config.yml`

## Test Data

### Default Test Data

Tests use these defaults:
- **Store**: `DEFAULT`
- **Languages**: `en`, `fr`
- **Currency**: `CAD`
- **Country**: `CA` (Canada)

### Test Resources

**Location**: `sm-core/src/test/resources/`

```
resources/
├── img/
│   └── icon.png          # Test image
├── sql/
│   └── test-data.sql     # SQL test data
├── database.properties   # Test DB config
├── shopizer-core.properties
└── application.properties
```

## Next Steps

1. **Enable Skipped Tests**
   - Configure CMS for file storage tests
   - Setup SMTP for email tests
   - Configure payment gateway for order tests

2. **Increase Coverage**
   - Add tests for uncovered services
   - Add edge case tests
   - Add error handling tests

3. **Add API Tests**
   - Test REST endpoints
   - Test authentication
   - Test authorization

4. **Performance Tests**
   - Load testing
   - Stress testing
   - Database query optimization

## Related Documentation

- [Getting Started](05-getting-started.md) - Setup guide
- [Architecture](03-architecture.md) - System architecture
- [Development Workflow](08-development-workflow.md) - Development practices

---

**Last Updated**: 2026-02-25
**Test Status**: ✅ 28 tests, 14 passing, 14 skipped, 0 failing
