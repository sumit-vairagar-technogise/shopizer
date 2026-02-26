# FAQ - Frequently Asked Questions

## General Questions

### Q: What is Shopizer?
**A**: Shopizer is an open-source, headless e-commerce platform built with Java and Spring Boot. It provides REST APIs for building online stores with any frontend technology.

### Q: Is Shopizer free to use?
**A**: Yes, Shopizer is open-source under the Apache License 2.0. You can use it freely for commercial and non-commercial projects.

### Q: Does Shopizer include a frontend?
**A**: No, Shopizer is a headless backend. You need to build your own frontend or use the separate React-based admin and storefront projects.

### Q: What databases does Shopizer support?
**A**: MySQL (recommended), PostgreSQL, Oracle, and H2 (for development).

## Technical Questions

### Q: What Java version do I need?
**A**: Java 11 minimum, Java 17 is tested and supported.

### Q: Can I use Shopizer with Spring Boot 3.x?
**A**: Not currently. The project uses Spring Boot 2.5.12. Migration to Spring Boot 3.x would require significant changes.

### Q: How do I enable HTTPS?
**A**: Configure SSL in `application.properties`:
```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your-password
server.ssl.key-store-type=PKCS12
```

### Q: How do I change the default port?
**A**: Edit `application.properties`:
```properties
server.port=8081
```

### Q: Where are the logs stored?
**A**: By default, logs go to console. To write to file:
```properties
logging.file.name=shopizer.log
```

## Development Questions

### Q: How do I add a new API endpoint?
**A**: 
1. Create controller in `sm-shop/api/v1/`
2. Create facade in `sm-shop/facade/`
3. Add service method in `sm-core/services/`
4. Add Swagger annotations

### Q: What's the difference between Mapper and Populator?
**A**: 
- **Mapper**: New pattern using interfaces (MapStruct-style)
- **Populator**: Legacy pattern, being phased out
- Use Mappers for new code

### Q: How do I add a new entity?
**A**:
1. Create entity in `sm-core-model/model/`
2. Add repository in `sm-core/repositories/`
3. Create service in `sm-core/services/`
4. Create DTOs in `sm-shop-model/`

### Q: Build fails with test failures - what should I do?
**A**: For initial setup, skip tests:
```bash
mvn clean install -DskipTests
```
This is normal for first-time setup. You can investigate and fix tests later once the app is running.

To see which tests failed:
```bash
# View test reports
cat sm-core/target/surefire-reports/*.txt

# Run tests with details
cd sm-core && mvn test -e
```

### Q: How do I run tests?
**A**:
```bash
# All tests
mvn test

# Skip tests during build
mvn clean install -DskipTests

# Specific module
cd sm-core && mvn test

# Single test class
mvn test -Dtest=ProductServiceTest
```

## Database Questions

### Q: How do I reset the database?
**A**: 
For H2: Delete `SALESMANAGER.h2.db` file and restart
For MySQL: Drop and recreate database

### Q: How do I see SQL queries?
**A**: Enable SQL logging in `application.properties`:
```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Q: What is the default admin username/password?
**A**: Check the initialization data in `sm-core/resources/` or database after first run. Typically:
- Username: `admin@shopizer.com`
- Password: `password` (should be changed)

### Q: How do I change the database schema name?
**A**: Edit `application.properties`:
```properties
spring.jpa.properties.hibernate.default_schema=YOUR_SCHEMA
```

## API Questions

### Q: How do I authenticate API requests?
**A**: 
1. Login via `/api/v1/auth/login` to get JWT token
2. Include token in header: `Authorization: Bearer <token>`

### Q: What's the difference between v1 and v2 APIs?
**A**: 
- v1: Original API version
- v2: Enhanced version with backward compatibility
- Use v1 for most cases

### Q: How do I test APIs without Swagger?
**A**: Use curl, Postman, or any HTTP client:
```bash
curl -X GET http://localhost:8080/api/v1/store/DEFAULT
```

### Q: Why am I getting 401 Unauthorized?
**A**: 
- Check if endpoint requires authentication
- Verify JWT token is valid and not expired
- Ensure token is in Authorization header

## Multi-Store Questions

### Q: How do I create a new store?
**A**: Use the Merchant Store API:
```bash
POST /api/v1/private/store
```

### Q: How does multi-tenancy work?
**A**: Each store (MerchantStore) is a tenant. Data is isolated by `merchantStore.id` foreign key.

### Q: Can stores share products?
**A**: No, products are store-specific. You'd need to duplicate products across stores.

## Deployment Questions

### Q: How do I deploy to production?
**A**:
1. Build: `mvn clean package -DskipTests`
2. Copy JAR from `sm-shop/target/`
3. Run: `java -jar sm-shop-3.2.5.jar --spring.profiles.active=mysql`

### Q: Can I run Shopizer in Docker?
**A**: Yes, Dockerfile is included:
```bash
docker build -t shopizer .
docker run -p 8080:8080 shopizer
```

### Q: How do I configure for AWS/GCP?
**A**: Use the cloud profiles in `sm-shop/resources/profiles/aws` or `gcp`

### Q: What are the system requirements?
**A**:
- **CPU**: 2+ cores
- **RAM**: 2GB minimum, 4GB recommended
- **Disk**: 1GB for application, more for database
- **Java**: 11 or 17

## Performance Questions

### Q: How do I enable caching?
**A**: Caching is enabled by default using Ehcache. Configure in `ehcache.xml`.

### Q: How do I optimize database queries?
**A**:
- Enable query logging to identify slow queries
- Add database indexes
- Use pagination for large result sets
- Enable second-level cache

### Q: Can Shopizer handle high traffic?
**A**: Yes, with proper configuration:
- Use connection pooling (HikariCP)
- Enable caching
- Use load balancer for multiple instances
- Optimize database

## Integration Questions

### Q: How do I add a payment gateway?
**A**: Implement `PaymentModule` interface in `sm-core-modules/integration/payment/`

### Q: How do I integrate with Elasticsearch?
**A**: Configure in `application.properties`:
```properties
elasticsearch.cluster.name=shopizer
elasticsearch.server.host=localhost
elasticsearch.server.port=9300
```

### Q: Can I use AWS S3 for file storage?
**A**: Yes, configure CMS module to use S3FileManager

### Q: How do I send emails?
**A**: Configure SMTP in `email.properties`:
```properties
email.host=smtp.gmail.com
email.port=587
email.username=your-email@gmail.com
email.password=your-password
```

## Troubleshooting

### Q: Application won't start
**A**: Check:
- Java version (11 or 17)
- Port 8080 is available
- Database connection (if using MySQL)
- Console logs for errors

### Q: Getting "Table not found" errors
**A**: 
- Ensure `hibernate.hbm2ddl.auto=update` in config
- Check database schema name matches config
- Verify database user has CREATE permissions

### Q: Swagger UI shows empty
**A**:
- Clear browser cache
- Check console for JavaScript errors
- Try `/swagger-ui/index.html` instead

### Q: Build fails with dependency errors
**A**:
```bash
# Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

## Best Practices

### Q: Should I modify core modules?
**A**: Avoid modifying `sm-core` and `sm-core-model` directly. Extend functionality through:
- Custom services
- Module implementations
- Facade layer customizations

### Q: How should I structure custom code?
**A**: Create separate packages:
```
com.yourcompany.shopizer.custom
├── api/
├── facade/
└── service/
```

### Q: Should I use Populators or Mappers?
**A**: Use Mappers for new code. Populators are legacy.

### Q: How do I handle API versioning?
**A**: Create new package for new version:
```
sm-shop/api/v3/
```

## Getting Help

### Q: Where can I get help?
**A**:
- GitHub Issues: https://github.com/shopizer-ecommerce/shopizer/issues
- Stack Overflow: Tag `shopizer`
- Documentation: https://shopizer-ecommerce.github.io/documentation/

### Q: How do I report a bug?
**A**: Create an issue on GitHub with:
- Shopizer version
- Java version
- Steps to reproduce
- Error logs
- Expected vs actual behavior

### Q: Can I contribute to Shopizer?
**A**: Yes! See [Development Workflow](08-development-workflow.md) for contribution guidelines.

## Additional Resources

- **Official Site**: http://www.shopizer.com
- **GitHub**: https://github.com/shopizer-ecommerce/shopizer
- **Documentation**: https://shopizer-ecommerce.github.io/documentation/
- **Docker Hub**: https://hub.docker.com/r/shopizerecomm/shopizer
