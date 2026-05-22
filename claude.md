# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此代码仓库中工作时提供指导。

## 项目概述

`jun_java_plugin` 是一个全面的 Java 企业级开发组件库，提供开箱即用、生产级别的代码示例和模板。该项目通过提供 200+ 个集成类库消除重复编码，涵盖四大主要类别：

- **jun_java_plugins**: 60+ 原生 Java 开发组件（无 Spring 依赖）
- **jun_springboot_plugin**: 100+ Spring Boot 集成示例
- **jun_springcloud_plugin**: Spring Cloud 微服务组件（Netflix、Alibaba、Dubbo）
- **jun_springboot_starter**: 自定义 Spring Boot 启动器
- **java_project_template**: Maven 项目模板（SSH、SSM、Spring Boot、Spring Cloud）

这是一个**演示和参考仓库**，而非生产应用。每个模块都是独立的示例，展示集成模式。

## 构建命令

\`\`\`bash
# 清理并编译整个项目（可能需要增加 JVM 内存）
mvn clean compile

# 使用内存配置构建（如果编译失败）
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
mvn clean install -DskipTests

# 构建特定模块
cd jun_java_plugins && mvn clean package

# 构建 SpringBoot 插件模块（耗时约 2 分钟）
cd jun_springboot_plugin && mvn clean compile

# 构建 SpringCloud 插件模块
cd jun_springcloud_plugin && mvn clean compile
\`\`\`

**重要说明：**
- 某些模块可能因依赖冲突而有编译错误 - 这在演示仓库中是正常的
- 并非所有模块都能一起运行；它们是独立的示例
- 使用 `-DskipTests` 在构建时跳过测试
- 父 POM 设计简洁 - 每个模块管理自己的依赖

## 项目结构

```
jun_java_plugin/
├── jun_java_plugins/          # 原生 Java 组件（无 Spring）
│   ├── jun_algorithm/         # 算法和数据结构
│   ├── jun_dbutil/            # JDBC 和数据库工具
│   ├── jun_redis/             # Redis 客户端集成
│   ├── jun_quartz/            # 任务调度
│   ├── jun_freemarker/        # 代码生成模板引擎
│   └── [60+ 其他模块]
│
├── jun_springboot_plugin/     # Spring Boot 集成
│   ├── springboot_codegen/    # 代码生成器（Velocity 模板）
│   ├── springboot_mybatis/    # MyBatis 集成
│   ├── springboot_redis/      # Spring Boot 的 Redis
│   ├── springboot_security2/  # Spring Security 示例
│   ├── springboot_oauth2/     # OAuth2 授权/资源服务器
│   └── [100+ 其他模块]
│
├── jun_springboot_starter/    # 自定义 Spring Boot 启动器
│   ├── jun-encrypt-body-spring-boot-starter/
│   ├── jun-minio-spring-boot-starter/
│   ├── jun-p6spy-spring-boot-starter/
│   └── [其他自定义启动器]
│
├── jun_springcloud_plugin/    # Spring Cloud 微服务
│   └── [Netflix、Alibaba、Dubbo 示例]
│
└── java_project_template/     # Maven 项目模板
    └── [单模块、多模块、SSH、SSM 模板]
```

## 代码生成

项目包含两种代码生成方式：

### 1. 内置代码生成器（`springboot_codegen`）

位置：`jun_springboot_plugin/springboot_codegen/`

- 使用 Velocity 模板生成 Entity/Mapper/Service/Controller
- 运行后可通过 http://localhost:8080/demo/index.html 访问 Web 界面
- 生成基于 MyBatis-Plus 的 CRUD 代码，使用 Lombok 注解
- 支持 MySQL 数据库

### 2. Claude Code 技能（`/crud` 命令）

仓库包含自定义 Claude Code 技能：

- **`/crud`**: 生成完整业务模块（SQL → Entity → DAO → XML → Repository → Service → Controller → Mapper → POJO）
- **`/project-conventions`**: 显示项目编码规范（阿里巴巴 Java 开发手册）

使用方法：输入 `/crud` 后跟需求描述，例如 "/crud 创建一个商品分类管理模块"

## 编码规范

项目遵循**阿里巴巴 Java 开发手册**约定：

### 命名约定
- **包名**: 全小写，反向域名格式（`net.trueland.tshop.retail.system.center`）
- **类名**: 大驼峰 + 后缀（`ApplicationService`、`ApplicationDao`、`ApplicationDTO`）
- **方法名**: 小驼峰 + 前缀（`getById()`、`listByQo()`、`add()`、`updateById()`）
- **常量**: 全大写下划线分隔（`MAX_PAGE_SIZE`、`DEFAULT_STATUS`）
- **数据库表**: 蛇形命名 + 前缀（`s_application`、`t_order`）
  - `s_` 系统表
  - `t_` 业务表

### 分层架构

```
Controller（Web 层）
    ↓ 调用
Service（业务逻辑层）
    ↓ 调用
Repository（数据仓储层）
    ↓ 调用
DAO（数据访问层 - MyBatis）
    ↓ 操作
Database（数据库）
```

### 必需数据库字段

每个表必须包含：
- `id` (BIGINT): 主键，自增
- `tenant_id` (BIGINT): 租户 ID，用于多租户
- `create_time` (DATETIME): 创建时间
- `update_time` (DATETIME): 更新时间
- `is_deleted` (TINYINT): 逻辑删除标记（0=否，1=是）

### 注解规范

```java
// Controller
@Api(tags = "模块描述")
@RestController
@RequiredArgsConstructor
@RequestMapping("/path")

// Service
@Service
@AllArgsConstructor
@Slf4j

// Repository
@Repository
@RequiredArgsConstructor

// Entity/DTO/VO
@Data
@ApiModelProperty("字段描述")
```

### 异常处理

- 使用自定义 `ServiceException` 和错误码
- Repository 层验证数据存在性
- Controller 返回统一的 `ApiResult<T>` 格式
- 永远不要捕获异常后不处理（禁止空 catch 块）

## 技术栈

| 类别 | 技术 | 说明 |
|------|------|------|
| Java | JDK 1.8 | 必需 |
| 构建工具 | Maven 3.5+ | 必需 |
| IDE | IDEA 2018.2+ / STS 4.5+ | 需安装 Lombok 插件 |
| Spring Boot | 2.4.2 / 2.6.13 | 不同模块使用不同版本 |
| ORM | MyBatis、MyBatis-Plus、Hibernate、JPA | 多种示例 |
| 对象映射 | MapStruct | 推荐用于 DTO/Entity 转换 |
| 工具库 | Lombok、Hutool、Apache Commons | 广泛使用 |
| API 文档 | Swagger、Knife4j | 多数模块包含 |
| 数据库 | MySQL 5.7/8.0 | 主要数据库 |
| 缓存 | Redis | 多种集成示例 |
| 消息队列 | RabbitMQ、RocketMQ、Kafka | 各种示例 |
| 工作流 | Activiti、Flowable、Snaker | 流程引擎 |
| 任务调度 | Quartz、XXL-Job、Elastic-Job | 任务调度 |

## 常见开发模式

### 探索模块时

1. 每个模块都是自包含的，有自己的 README.md
2. 检查 `src/main/resources/application.yml` 了解配置
3. 查找 `*Application.java` 主类理解启动方式
4. 检查 `pom.xml` 了解具体依赖和版本

### 添加新功能时

1. 使用 `/crud` 技能生成完整模块
2. 遵循模块中现有的包结构
3. 保持与项目编码约定的一致性
4. 添加适当的 Swagger 注解进行 API 文档化

### 调试时

1. 检查模块特定的 `application.yml` 配置
2. 查看 `src/main/resources/mybatis/mapper/` 中的 MyBatis XML 映射
3. 在 `logback.xml` 中为特定包启用 DEBUG 日志
4. 许多模块在 `src/test/java/` 中有测试类

## 重要说明

1. **内存需求**: 构建整个项目可能需要增加 JVM 堆大小（`-Xmx2048m`）
2. **模块独立性**: 模块是独立示例，一起运行可能有依赖冲突
3. **非生产就绪**: 这是学习/参考仓库。模块展示集成模式，但可能缺乏生产级的错误处理、安全性和优化
4. **中文文档**: 大多数 README 文件和注释是中文的
5. **混合版本**: 不同模块故意使用不同的 Spring Boot 版本以展示兼容性
6. **Git 状态**: 仓库可能有未提交的文件（`nul`、`prompt.md`）- 这些是工作文件，可以忽略
7. **需要 Lombok**: IDE 必须安装 Lombok 插件以避免编译错误

## 使用此代码库时

- **将每个模块视为独立示例**，而非统一应用的一部分
- **不要试图"修复"不同模块间的依赖冲突** - 它们是故意隔离的
- **生成新代码时参考 `/project-conventions` 技能**以确保一致性
- **使用 `/crud` 技能**生成遵循项目标准的样板代码
- **查看模块特定的 README** 文件了解使用说明和配置详情
- **创建新功能时**，在适当的插件目录中按现有模式生成代码
