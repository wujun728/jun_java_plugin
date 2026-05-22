# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 2.2.2 + MyBatis-Plus 3.3.0 API seed project for rapid development of small to medium-sized REST APIs. It features a Redis-based token authentication system, unified response handling, code generation, and multi-datasource support.

**Tech Stack**: Spring Boot, MyBatis-Plus, Redis, Druid, Knife4j (Swagger), MySQL, FastJSON, Lombok

## Development Commands

### Build and Run
```bash
# Build project
mvn clean package

# Run application (default port 8080)
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest
```

### Code Generation
1. Configure database connection in `src/test/java/CodeGenerator.java` (lines 40-44)
2. Set `tableName` variable (line 18) to target table(s) - comma-separated for multiple
3. Run `CodeGenerator.main()` to generate: Model, Mapper, Service, ServiceImpl, Controller, Mapper XML
4. Refresh project directory to see generated files
5. Generated files follow project structure: `web/`, `service/`, `dao/`, `model/`, `resources/mapper/`

### Database
- Import test database: `test/mysql/user.sql` into MySQL
- Configure datasource in `src/main/resources/application-dev.yml`
- Default database name: `project`, username: `root`, password: `123456`

### API Documentation
- Access Knife4j UI: http://localhost:8080/doc.html
- Disable in production: set `knife4j.production: true` in config

## Architecture Overview

### Package Structure
```
com.company.project/
├── web/              # Controllers (@RestController) - REST endpoints
├── service/          # Business logic interfaces (IService extensions)
│   └── impl/        # Service implementations (ServiceImpl extensions)
├── dao/              # MyBatis mappers (extends BaseMapper<T>)
├── model/            # JPA entities with Lombok annotations
├── configurer/       # Spring configurations & interceptors
├── core/             # Core utilities (Result, ServiceException, etc.)
└── utils/            # Helper utilities (MD5, ImageCode, etc.)
```

### Authentication & Session Management

**Token-Based Authentication (Redis)**:
- Token format: `{32-random-chars}#{userId}` (e.g., `aBcD1234efgh...#123`)
- Token stored in Redis with key `user:token:{token}`, TTL: 7 days (604800s)
- Token can be passed via:
  - Header: `Authorization: {token}` (preferred)
  - Request parameter: `?token={token}` (fallback)
- Login endpoint: `POST /api/user/login` with `{"username":"admin","password":"123456"}`
- Returns token in response, use it in all subsequent requests

**Session Service** (`HttpSessionService`):
- `createTokenAndUser(User)` - Generate token and store session
- `getCurrentSession()` - Retrieve session from current request token
- `abortUserByToken()` - Logout current session (single token)
- `abortAllUserByToken()` - Invalidate all tokens for current user (used on password change)
- `abortUserByUserId(userId)` - Admin function to force logout specific user

**Login Security**:
- Password hashing: Double MD5 with salt (`MD5(MD5(password + "springboot_api"))`)
- Failed login tracking: Max 5 attempts, locks account for 1 hour
- Redis key for tracking: `user:password:error:{username}`
- Single/Multi-login mode: Configure via `redis.allowMultipleLogin` (default: false)

**LoginInterceptor** (`configurer/LoginInterceptor.java`):
- Intercepts all requests except: `/api/user/login`, `/api/user/register`, `/doc.html`, Swagger resources
- Validates token presence and validity
- Returns 401 UNAUTHORIZED if token missing/invalid

### Unified Response Handling

**Result Wrapper** - All API responses use this structure:
```json
{
  "code": 200,
  "success": true,
  "message": "SUCCESS",
  "data": {...}
}
```

**ResultCode Enum**:
- 200: SUCCESS
- 400: FAIL (business errors)
- 401: UNAUTHORIZED
- 404: NOT_FOUND
- 500: INTERNAL_SERVER_ERROR
- 10001: PARAM_FAIL (validation errors)

**Usage in Controllers**:
```java
return ResultGenerator.genSuccessResult(data);
return ResultGenerator.genFailResult("Error message");
```

**Exception Handling** (`WebMvcConfigurer.java`):
- `ServiceException` → Auto-wrapped into Result with code 400
- `MethodArgumentNotValidException` → Code 10001 (validation)
- `NoHandlerFoundException` → Code 404
- Generic exceptions → Code 500
- **Best Practice**: Throw `new ServiceException("message")` for business failures, will be auto-wrapped

### Multi-Datasource Support

**Configuration** (in `application-dev.yml`):
```yaml
spring.datasource.dynamic:
  primary: master  # Default datasource
  datasource:
    master: {...}   # Primary DB
    slave_1: {...}  # Secondary DB
    slave_2: {...}  # Tertiary DB
```

**Usage**: Add `@DS("slave_1")` annotation on service methods or classes to switch datasource

**Important**: Uses `dynamic-datasource-spring-boot-starter` v2.5.5

### MyBatis-Plus Features

**Base Operations** (No SQL needed):
- All services extend `ServiceImpl<Mapper, Entity>`, which provides: `save()`, `saveOrUpdate()`, `removeById()`, `updateById()`, `getById()`, `list()`, `page()`
- All mappers extend `BaseMapper<Entity>` with CRUD methods

**Pagination**:
- Configured via `PaginationInterceptor` in `MyBatisPlusConfig`
- Usage: `Page<User> page = new Page<>(pageNum, pageSize); userService.page(page, queryWrapper);`

**Logic Delete**:
- Field name: `del_flag` (configurable in CodeGenerator line 20)
- Deletes set field to 1, queries auto-filter deleted records
- Config in `application-dev.yml`: `logic-delete-value: 1`, `logic-not-delete-value: 0`

**Custom Queries**:
- Define in Mapper interface: `List<User> customQuery(@Param("param") String param);`
- XML in `src/main/resources/mapper/UserMapper.xml`

### Model/Entity Guidelines

**Annotations**:
- `@TableName("table_name")` - Map to specific table
- `@TableId(type = IdType.AUTO)` - Primary key with auto-increment
- `@TableLogic` - Mark logic delete field
- `@TableField(exist = false)` - Non-database fields (e.g., DTOs, transient data)

**DTO Pattern**:
- If extending model with non-DB fields (e.g., join queries), prefer creating separate DTO class
- Alternative: Use `@TableField(exist = false)` on extended fields in Model

**Lombok Support**:
- All models use: `@Data`, `@EqualsAndHashCode(callSuper = false)`, `@Accessors(chain = true)`

## Key Configuration Files

**Profile-specific configs**:
- `application.yml` - Base configuration (active profile: dev, server port: 8080)
- `application-dev.yml` - Development (local MySQL, Redis, multi-datasource setup)
- `application-test.yml` - Testing environment
- `application-prod.yml` - Production (knife4j docs disabled)

**Redis Configuration** (`application-dev.yml`):
```yaml
redis:
  key.prefix.userToken: "user:token:"
  key.prefix.passwordError: "user:password:error:"
  key.expire.userToken: 604800    # 7 days
  key.expire.passwordError: 3600  # 1 hour
  allowMultipleLogin: false       # Single login mode
```

**MyBatis-Plus Configuration**:
```yaml
mybatis-plus:
  configuration.log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:mapper/*.xml
```

## Development Patterns

### Creating New Endpoints

1. **Generate Code**: Use `CodeGenerator` for table-based CRUD
2. **Service Layer**: Business logic in `service/impl/`, throw `ServiceException` for errors
3. **Controller Layer**: REST endpoints in `web/`, use `ResultGenerator` for responses
4. **Request Validation**: Use `@Valid` on request bodies with validation annotations
5. **Authentication**: Protected endpoints auto-validated by `LoginInterceptor`
6. **Get Current User**: `JSONObject session = httpSessionService.getCurrentSession();`
   - Returns: `{"userId": 123, "username": "admin"}`

### Working with Redis

Use `RedisService` for all Redis operations:
```java
@Autowired
private RedisService redisService;

redisService.set(key, value, expireSeconds);
String value = redisService.get(key);
boolean exists = redisService.exists(key);
redisService.remove(key);
redisService.delKeys(pattern);  // Pattern-based deletion
```

### Custom Exception Handling

Throw `ServiceException` anywhere in service/controller layer:
```java
if (user == null) {
    throw new ServiceException("User not found");
}
```
Will automatically return: `{"code": 400, "success": false, "message": "User not found"}`

## Important Files Reference

- `Application.java` - Main entry point, enables mapper scanning
- `WebMvcConfigurer.java` - Global exception handler, CORS, interceptors
- `LoginInterceptor.java` - Token validation interceptor
- `HttpSessionService.java` - Session lifecycle management
- `RedisService.java` - Redis operations wrapper
- `UserController.java` - Example auth endpoints (login, register, logout, password change)
- `CodeGenerator.java` - Code generation tool (in test directory)

## Testing API

**Login Flow**:
```bash
# 1. Login
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# Response: {"code":200,"data":{"token":"xyz123...#1","username":"admin"}}

# 2. Use token in subsequent requests
curl -X GET http://localhost:8080/api/user/getById/1 \
  -H "Authorization: xyz123...#1"
```

**Access Documentation**: http://localhost:8080/doc.html (Knife4j UI with all endpoints)
