# Getting Started

## Prerequisites

### Required Software
- **Java 11 or 17** (JDK)
- **Maven 3.6+** (or use included Maven wrapper)
- **Git**

### Recommended Tools
- **IDE**: IntelliJ IDEA (recommended), Eclipse, or VS Code
- **API Testing**: Postman, Insomnia, or curl
- **Database Client**: DBeaver, MySQL Workbench (optional)
- **Docker** (optional, for containerized deployment)

## Step 1: Verify Prerequisites

```bash
# Check Java version
java -version
# Should show: java version "11.x.x" or "17.x.x"

# Check Maven version
mvn -version
# Should show: Apache Maven 3.6.x or higher

# Check Git
git --version
```

## Step 2: Clone the Repository

```bash
# Clone the repository
git clone https://github.com/shopizer-ecommerce/shopizer.git

# Navigate to project directory
cd shopizer
```

## Step 3: Build the Project

```bash
# Clean and build all modules
mvn clean install

# This will:
# 1. Download all dependencies
# 2. Compile all modules
# 3. Run tests
# 4. Package the application
```

**Expected Output**:
```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] shopizer ........................................... SUCCESS
[INFO] sm-core-model ...................................... SUCCESS
[INFO] sm-core-modules .................................... SUCCESS
[INFO] sm-core ............................................ SUCCESS
[INFO] sm-shop-model ...................................... SUCCESS
[INFO] sm-shop ............................................ SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

**Build Time**: First build takes 5-10 minutes (downloads dependencies)

## Step 4: Run the Application

### Option A: Using Maven (Recommended for Development)

```bash
# Navigate to main application module
cd sm-shop

# Run with Spring Boot Maven plugin
mvn spring-boot:run
```

### Option B: Using Java JAR

```bash
# From project root, after building
cd sm-shop/target

# Run the JAR file
java -jar sm-shop-3.2.5.jar
```

### Option C: Using IDE

**IntelliJ IDEA**:
1. Open project: `File → Open → Select shopizer folder`
2. Wait for Maven import to complete
3. Navigate to: `sm-shop/src/main/java/com/salesmanager/shop/application/ShopApplication.java`
4. Right-click → `Run 'ShopApplication'`

**Eclipse**:
1. Import project: `File → Import → Existing Maven Projects`
2. Select shopizer folder
3. Navigate to `ShopApplication.java`
4. Right-click → `Run As → Java Application`

## Step 5: Verify Application is Running

### Check Console Output

Look for these lines in the console:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.5.12)

...
Started ShopApplication in 15.234 seconds (JVM running for 16.789)
```

### Access the Application

Open your browser and navigate to:

**Swagger UI** (API Documentation):
```
http://localhost:8080/swagger-ui.html
```

**Health Check**:
```
http://localhost:8080/actuator/health
```

Expected response:
```json
{
  "status": "UP"
}
```

## Step 6: Explore Swagger UI

1. Open `http://localhost:8080/swagger-ui.html`
2. You'll see all available API endpoints organized by category:
   - Product APIs
   - Category APIs
   - Customer APIs
   - Order APIs
   - Shopping Cart APIs
   - etc.

### Try Your First API Call

**Get Store Information**:
1. Find `merchant-store-api` section
2. Click on `GET /api/v1/store/{code}`
3. Click "Try it out"
4. Enter `DEFAULT` as the store code
5. Click "Execute"

Expected response:
```json
{
  "code": "DEFAULT",
  "name": "Default Store",
  "currency": "CAD",
  "defaultLanguage": "en",
  ...
}
```

## Step 7: Database Setup

### Default Configuration (H2 In-Memory)

By default, Shopizer uses **H2 in-memory database**:
- No setup required
- Data is lost when application stops
- Good for development and testing
- Database file: `sm-shop/SALESMANAGER.h2.db`

### Access H2 Console (Optional)

Add to `application.properties`:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./SALESMANAGER`
- Username: `sa`
- Password: (leave empty)

### Switch to MySQL (Production)

1. **Install MySQL**:
```bash
# Create database
mysql -u root -p
CREATE DATABASE SALESMANAGER;
CREATE USER shopizer IDENTIFIED BY 'your-password';
GRANT ALL ON SALESMANAGER.* TO shopizer;
FLUSH PRIVILEGES;
```

2. **Update Configuration**:

Edit `sm-shop/src/main/resources/profiles/mysql/database.properties`:
```properties
db.jdbcUrl=jdbc:mysql://localhost:3306/SALESMANAGER?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8
db.user=shopizer
db.password=your-password
db.driverClass=com.mysql.cj.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

3. **Run with MySQL Profile**:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Step 8: IDE Setup

### IntelliJ IDEA

**Import Project**:
1. `File → Open`
2. Select `shopizer` folder
3. Wait for Maven import

**Configure Run Configuration**:
1. `Run → Edit Configurations`
2. Click `+` → `Spring Boot`
3. Name: `Shopizer`
4. Main class: `com.salesmanager.shop.application.ShopApplication`
5. Module: `sm-shop`
6. Click `OK`

**Enable Lombok** (if using):
1. Install Lombok plugin
2. `Settings → Build → Compiler → Annotation Processors`
3. Enable annotation processing

### VS Code

**Install Extensions**:
- Java Extension Pack
- Spring Boot Extension Pack
- Maven for Java

**Open Project**:
1. `File → Open Folder`
2. Select `shopizer` folder
3. Wait for Java extension to load

**Run**:
- Press `F5` or use Run/Debug panel
- Select `ShopApplication` main class

## Step 9: Test the Setup

### Run a Complete Flow

1. **Get Store Info**:
```bash
curl http://localhost:8080/api/v1/store/DEFAULT
```

2. **List Categories**:
```bash
curl http://localhost:8080/api/v1/category?store=DEFAULT&lang=en
```

3. **List Products**:
```bash
curl http://localhost:8080/api/v1/products?store=DEFAULT&lang=en
```

### Check Logs

Logs are output to console. Look for:
- `INFO` level messages for normal operations
- `ERROR` level for issues
- SQL queries (if `logging.level.org.hibernate.SQL=DEBUG`)

## Common Issues & Solutions

### Issue: Port 8080 Already in Use

**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Build Fails with "Tests Failed"

**Error Message**:
```
[ERROR] Failed to execute goal maven-surefire-plugin:test on project sm-core: 
There are test failures.
```

**Solution**: Skip tests during initial build:
```bash
mvn clean install -DskipTests
```

**Why This Happens**:
- Tests may require specific database setup
- Environment differences (OS, Java version)
- Missing test data or configuration
- Flaky tests

**To Investigate**:
```bash
# View test reports
cat sm-core/target/surefire-reports/*.txt

# Run with error details
cd sm-core && mvn test -e
```

**Best Practice**: Get the app running first, fix tests later once you understand the codebase.

### Issue: Build Fails with "Tests Failed"

**Solution**: Skip tests during build:
```bash
mvn clean install -DskipTests
```

### Issue: Out of Memory Error

**Solution**: Increase Maven memory:
```bash
export MAVEN_OPTS="-Xmx1024m"
mvn clean install
```

### Issue: Cannot Connect to Database

**Solution**: Check database configuration in:
- `sm-shop/src/main/resources/profiles/[profile]/database.properties`
- Verify database is running
- Check credentials

### Issue: Swagger UI Not Loading

**Solution**: 
- Clear browser cache
- Check console for errors
- Verify application started successfully
- Try: `http://localhost:8080/swagger-ui/index.html`

## Project Configuration Files

### Key Configuration Locations

```
sm-shop/src/main/resources/
├── application.properties          # Main config
├── profiles/
│   ├── local/database.properties   # H2 config
│   └── mysql/database.properties   # MySQL config
└── shopizer-properties.properties  # App-specific config
```

### Important Properties

**application.properties**:
```properties
# Server
server.port=8080

# Database schema
spring.jpa.properties.hibernate.default_schema=SALESMANAGER

# Hibernate DDL
hibernate.hbm2ddl.auto=update

# Logging
logging.level.com.shopizer=INFO
logging.level.org.hibernate.SQL=ERROR
```

## Next Steps

Now that you have the application running:

1. **Explore APIs**: Continue to [API Guide](06-api-guide.md)
2. **Understand Data**: Check [Database Schema](07-database-schema.md)
3. **Start Coding**: Review [Development Workflow](08-development-workflow.md)

## Quick Reference Commands

```bash
# Build project
mvn clean install

# Run application
cd sm-shop && mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=mysql

# Skip tests
mvn clean install -DskipTests

# Run tests only
mvn test

# Package without running
mvn package -DskipTests

# Clean build artifacts
mvn clean
```

## Development Workflow

```
┌─────────────────────────────────────────────────────────────┐
│  1. Make Code Changes                                       │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│  2. Build Module (mvn clean install)                        │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│  3. Restart Application                                     │
│     (Spring Boot DevTools auto-restart if enabled)          │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│  4. Test via Swagger UI or Postman                          │
└─────────────────────────────────────────────────────────────┘
```

Congratulations! You now have Shopizer running locally. 🎉
