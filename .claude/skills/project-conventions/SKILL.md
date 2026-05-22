---
name: project-conventions
description: 项目编码规范指南，遵循阿里巴巴Java开发手册，确保代码风格统一
---

# project-conventions - 项目编码规范

本规范基于《阿里巴巴Java开发手册》，结合项目实际情况制定，适用于本项目所有Java代码开发。

## 一、项目结构规范

### 1.1 包结构

```
net.trueland.tshop.retail.system.center
├── domain/                      # 领域层
│   ├── entity/                  # 数据库实体类
│   ├── dao/                     # MyBatis Mapper接口
│   └── pojo/                    # 数据传输对象
│       └── {模块名}/            # 按功能模块分包
│           ├── {实体}DTO.java   # 数据传输对象
│           ├── {实体}VO.java    # 视图对象
│           └── {实体}SearchQO.java  # 查询对象
├── mapping/                     # MapStruct映射器
│   └── {模块名}/
├── repository/                  # 仓储层
│   └── {模块名}/
├── service/                     # 业务逻辑层
│   └── {模块名}/
├── web/                         # 控制器层
│   ├── admin/                   # 管理端API
│   ├── tenant/                  # 租户端API
│   ├── app/                     # C端API
│   └── feign/                   # Feign接口
├── client/                      # 第三方服务客户端
│   ├── feign/                   # Feign客户端
│   ├── retrofit/                # Retrofit客户端
│   ├── config/                  # 客户端配置
│   └── model/                   # 客户端模型
├── common/                      # 公共模块
│   ├── constants/               # 常量类
│   ├── enums/                   # 枚举类
│   ├── utils/                   # 工具类
│   └── ErrorCode.java           # 错误码
├── aspect/                      # AOP切面
├── annotation/                  # 自定义注解
├── manager/                     # 管理器
└── config/                      # 配置类
```

### 1.2 资源文件结构

```
src/main/resources/
├── mybatis/
│   └── mapper/                  # MyBatis XML映射文件
│       └── {实体名}Dao.xml
├── application.yml              # 主配置文件
├── application-{env}.yml        # 环境配置文件
└── bootstrap.yml                # 启动配置文件
```

---

## 二、命名规范

### 2.1 包命名

- **格式**：全部小写，使用点分隔
- **规则**：反向域名 + 项目名 + 模块名
- **示例**：`net.trueland.tshop.retail.system.center.service.application`

### 2.2 类命名

| 类型 | 命名格式 | 示例 |
|------|---------|------|
| Entity（实体类） | `{功能名}` | `Application`, `OrgUser` |
| DAO（数据访问） | `{功能名}Dao` | `ApplicationDao`, `OrgUserDao` |
| Repository（仓储） | `{功能名}Repository` | `ApplicationRepository` |
| Service（服务） | `{功能名}Service` | `ApplicationService` |
| Controller（控制器） | `{功能名}Controller` | `ApplicationController` |
| Mapper（对象映射） | `{功能名}Mapper` | `ApplicationMapper` |
| DTO（传输对象） | `{功能名}DTO` | `ApplicationDTO` |
| VO（视图对象） | `{功能名}VO` | `ApplicationVO` |
| QO（查询对象） | `{功能名}SearchQO` | `ApplicationSearchQO` |
| Enum（枚举） | `{功能名}Enum` | `EnableEnum`, `StatusEnum` |
| Constants（常量） | `{功能名}Constants` | `SystemConstants` |
| Utils（工具） | `{功能名}Utils` | `DateUtils`, `StringUtils` |
| Config（配置） | `{功能名}Config` | `WebConfig`, `RedisConfig` |
| Aspect（切面） | `{功能名}Aspect` | `LogAspect` |

### 2.3 方法命名

| 操作类型 | 命名前缀 | 示例 |
|---------|---------|------|
| 查询单个 | `get`, `find`, `query` | `getById()`, `findByName()` |
| 查询列表 | `list`, `select` | `listByQo()`, `selectAll()` |
| 新增 | `add`, `insert`, `create` | `add()`, `insertSelective()` |
| 修改 | `update`, `modify`, `edit` | `update()`, `updateById()` |
| 删除 | `delete`, `remove` | `deleteById()`, `removeByIds()` |
| 判断存在 | `exist`, `check`, `is` | `exist()`, `checkExists()` |
| 统计 | `count` | `countByStatus()` |

### 2.4 变量命名

- **格式**：小驼峰（lowerCamelCase）
- **示例**：`applicationName`, `createTime`, `isDeleted`
- **禁止**：拼音命名、无意义缩写

### 2.5 常量命名

- **格式**：全大写 + 下划线分隔
- **示例**：`MAX_PAGE_SIZE`, `DEFAULT_STATUS`

---

## 三、数据库规范

### 3.1 表命名

- **格式**：`s_` 或 `t_` 前缀 + 蛇形命名
- **前缀说明**：
  - `s_` - 系统表
  - `t_` - 业务表
- **示例**：`s_application`, `s_org_user`, `t_order`

### 3.2 字段命名

- **格式**：全小写 + 下划线分隔（蛇形命名）
- **示例**：`application_name`, `create_time`, `is_deleted`

### 3.3 必备字段

每个表必须包含以下基础字段：

| 字段名 | 类型 | 说明 |
|-------|------|------|
| `id` | BIGINT | 主键ID，自增 |
| `tenant_id` | BIGINT | 租户ID |
| `create_time` | DATETIME | 创建时间 |
| `update_time` | DATETIME | 更新时间 |
| `is_deleted` | TINYINT | 逻辑删除标记（0否/1是） |

### 3.4 字段类型映射

| 数据库类型 | Java类型 | 说明 |
|-----------|---------|------|
| BIGINT | Long | 主键、ID类型 |
| INT/TINYINT | Integer | 状态、计数 |
| VARCHAR | String | 字符串 |
| DATETIME/TIMESTAMP | LocalDateTime | 时间类型 |
| DECIMAL | BigDecimal | 金额类型 |
| TEXT | String | 长文本 |

---

## 四、分层规范

### 4.1 架构分层

```
Controller层（Web层）
    ↓ 调用
Service层（业务逻辑层）
    ↓ 调用
Repository层（数据仓储层）
    ↓ 调用
DAO层（数据访问层）
    ↓ 操作
Database（数据库）
```

### 4.2 各层职责

#### Controller层
- 接收HTTP请求，参数校验
- 调用Service处理业务
- 返回统一响应格式 `ApiResult<T>`
- **禁止**：包含业务逻辑

```java
@Api(tags = "应用管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/add")
    @ApiOperation(value = "新增应用")
    public ApiResult<Long> add(@Validated @RequestBody ApplicationDTO dto) {
        return ApiResult.ok(applicationService.add(dto));
    }
}
```

#### Service层
- 实现业务逻辑
- 事务控制（@Transactional）
- 对象转换（调用Mapper）
- 异常处理

```java
@Service
@AllArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationMapper applicationMapper;
    private final ApplicationRepository applicationRepository;

    public Long add(ApplicationDTO dto) {
        Application entity = applicationMapper.toEntity(dto);
        applicationRepository.addEntity(entity);
        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<ApplicationDTO> dtoList) {
        // 事务操作
    }
}
```

#### Repository层
- 封装DAO操作
- 数据存在性验证
- 异常转换
- 复杂查询组装

```java
@Repository
@RequiredArgsConstructor
public class ApplicationRepository {

    private final ApplicationDao applicationDao;

    public void addEntity(Application row) {
        applicationDao.insertSelective(row);
    }

    public Application exist(Long id) {
        Application data = applicationDao.selectByPrimaryKey(id);
        if (Objects.isNull(data)) {
            throw new ServiceException(ErrorCode.DATA_NOT_EXISTS);
        }
        return data;
    }

    public Application getById(Long id) {
        return applicationDao.selectByPrimaryKey(id);
    }
}
```

#### DAO层
- 纯数据访问接口
- MyBatis Mapper接口
- 不包含业务逻辑

```java
@Mapper
public interface ApplicationDao {

    int insert(Application row);

    int insertSelective(Application row);

    Application selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Application row);

    int deleteByPrimaryKey(Long id);

    List<Application> selectByQo(@Param(value = "qo") ApplicationSearchQO qo);
}
```

---

## 五、注解规范

### 5.1 类级注解

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

// DAO
@Mapper

// Entity/DTO/VO
@Data

// 配置类
@Configuration

// 切面
@Aspect
@Component
```

### 5.2 方法级注解

```java
// Controller方法
@PostMapping("/add")
@ApiOperation(value = "操作描述")

// 事务控制
@Transactional(rollbackFor = Exception.class)

// 异常处理
@SneakyThrows
```

### 5.3 字段级注解

```java
// 文档注解
@ApiModelProperty("字段描述")

// 验证注解
@NotNull(message = "xxx不能为空")
@NotEmpty(message = "xxx不能为空")
@NotBlank(message = "xxx不能为空")
@Size(max = 100, message = "xxx长度不能超过100")

// MyBatis参数
@Param(value = "paramName")
```

### 5.4 MapStruct注解

```java
@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application toEntity(ApplicationDTO dto);

    void updateEntity(ApplicationDTO dto, @MappingTarget Application entity);

    ApplicationVO toVO(Application entity);

    List<ApplicationVO> toVOList(List<Application> list);

    @Mapping(target = "targetField", source = "sourceField")
    SomeVO customMapping(SomeEntity entity);
}
```

---

## 六、注释规范

### 6.1 类注释

```java
/**
 * 类功能描述
 *
 * @author 作者名
 */
public class ClassName {
}
```

### 6.2 方法注释

```java
/**
 * 方法功能描述
 *
 * @param paramName 参数说明
 * @return 返回值说明
 */
public ReturnType methodName(ParamType paramName) {
}
```

### 6.3 字段注释

```java
/**
 * 字段描述（可选）
 */
@ApiModelProperty("字段描述")
private String fieldName;
```

### 6.4 行内注释

```java
// 单行注释，说明下一行代码的作用
doSomething();

/*
 * 多行注释
 * 说明复杂逻辑
 */
```

---

## 七、异常处理规范

### 7.1 错误码定义

```java
@Getter
@AllArgsConstructor
public enum ErrorCode implements IRespCode {

    // 通用错误码 10000-10999
    SYSTEM_ERROR(10000, HttpStatus.INTERNAL_SERVER_ERROR, "系统错误"),
    PARAM_ERROR(10001, HttpStatus.BAD_REQUEST, "参数错误"),
    DATA_NOT_EXISTS(10002, HttpStatus.OK, "数据不存在"),

    // 业务错误码 11000-11999
    APPLICATION_NOT_EXISTS(11000, HttpStatus.OK, "应用不存在"),
    APPLICATION_NAME_EXISTS(11001, HttpStatus.OK, "应用名称已存在"),
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
```

### 7.2 异常抛出

```java
// Repository层抛出业务异常
public Application exist(Long id) {
    Application data = applicationDao.selectByPrimaryKey(id);
    if (Objects.isNull(data)) {
        throw new ServiceException(ErrorCode.DATA_NOT_EXISTS);
    }
    return data;
}

// Service层抛出业务异常
public void add(ApplicationDTO dto) {
    if (checkNameExists(dto.getName())) {
        throw new ServiceException(ErrorCode.APPLICATION_NAME_EXISTS);
    }
    // ...
}
```

### 7.3 统一响应格式

```java
// 成功响应
return ApiResult.ok(data);
return ApiResult.ok();

// 失败响应
return ApiResult.err(ErrorCode.PARAM_ERROR);
return ApiResult.err(code, message);
```

---

## 八、工具类使用规范

### 8.1 对象工具（Hutool）

```java
import cn.hutool.core.util.ObjectUtil;

// 判空
ObjectUtil.isNull(obj)
ObjectUtil.isNotNull(obj)
ObjectUtil.isEmpty(collection)
ObjectUtil.isNotEmpty(collection)

// 相等判断
ObjectUtil.equal(obj1, obj2)
```

### 8.2 字符串工具

```java
import org.apache.commons.lang3.StringUtils;

// 判空
StringUtils.isEmpty(str)
StringUtils.isNotEmpty(str)
StringUtils.isBlank(str)
StringUtils.isNotBlank(str)
```

### 8.3 集合工具

```java
import cn.hutool.core.collection.CollUtil;

// 判空
CollUtil.isEmpty(list)
CollUtil.isNotEmpty(list)

// 转换
CollUtil.newArrayList(items)
```

### 8.4 JSON工具

```java
import com.alibaba.fastjson.JSON;

// 序列化
String json = JSON.toJSONString(obj);

// 反序列化
Object obj = JSON.parseObject(json, Class.class);
List<T> list = JSON.parseArray(json, Class.class);
```

---

## 九、日志规范

### 9.1 日志级别

| 级别 | 使用场景 |
|------|---------|
| ERROR | 系统错误、异常 |
| WARN | 警告信息、潜在问题 |
| INFO | 关键业务流程、重要操作 |
| DEBUG | 调试信息 |

### 9.2 日志使用

```java
@Slf4j
public class ApplicationService {

    public void process() {
        log.info("开始处理应用, applicationId: {}", applicationId);

        try {
            // 业务逻辑
            log.debug("处理详情: {}", details);
        } catch (Exception e) {
            log.error("处理应用失败, applicationId: {}, error: {}", applicationId, e.getMessage(), e);
            throw new ServiceException(ErrorCode.SYSTEM_ERROR);
        }

        log.info("应用处理完成, applicationId: {}", applicationId);
    }
}
```

### 9.3 日志规范

- 使用占位符 `{}` 而非字符串拼接
- 异常日志必须包含异常堆栈
- 敏感信息（密码、token）禁止打印
- 日志内容应包含关键业务标识

---

## 十、数据类型规范

| 场景 | Java类型 | 说明 |
|------|---------|------|
| 主键ID | Long | 64位整数 |
| 状态/类型 | Integer | 使用枚举值 |
| 布尔值 | Integer | 0/1表示 |
| 时间 | LocalDateTime | 不使用Date |
| 金额 | Long | 以分为单位 |
| 精确计算 | BigDecimal | 财务计算 |
| 字符串 | String | - |
| 集合 | List | 优先使用List |

---

## 十一、编码禁忌

1. **禁止**使用魔法数字，必须定义常量或枚举
2. **禁止**在循环中进行数据库操作
3. **禁止**捕获异常后不处理（空catch块）
4. **禁止**使用System.out.println，使用日志框架
5. **禁止**在Controller层写业务逻辑
6. **禁止**使用拼音命名
7. **禁止**使用过时的Date类，使用LocalDateTime
8. **禁止**直接使用HashMap存储业务数据，使用DTO/VO
9. **禁止**硬编码配置信息，使用配置文件
10. **禁止**返回null集合，返回空集合

---

## 十二、最佳实践

### 12.1 依赖注入

```java
// 推荐：构造函数注入
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
}

// 不推荐：字段注入
@Autowired
private ApplicationRepository applicationRepository;
```

### 12.2 判空处理

```java
// 推荐
if (Objects.isNull(obj)) { }
if (ObjectUtil.isEmpty(list)) { }

// 不推荐
if (obj == null) { }
if (list == null || list.size() == 0) { }
```

### 12.3 Optional使用

```java
// 查询可能为空的数据
public Optional<Application> findById(Long id) {
    return Optional.ofNullable(applicationDao.selectByPrimaryKey(id));
}

// 使用
applicationService.findById(id)
    .orElseThrow(() -> new ServiceException(ErrorCode.DATA_NOT_EXISTS));
```

### 12.4 Stream操作

```java
// 集合转换
List<Long> ids = list.stream()
    .map(Application::getId)
    .collect(Collectors.toList());

// 过滤
List<Application> filtered = list.stream()
    .filter(app -> Objects.equals(app.getStatus(), 1))
    .collect(Collectors.toList());

// 分组
Map<Integer, List<Application>> grouped = list.stream()
    .collect(Collectors.groupingBy(Application::getType));
```

---

## 十三、技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 2.4.2 / 2.6.13 |
| 微服务 | Spring Cloud | - |
| ORM框架 | MyBatis | - |
| 对象映射 | MapStruct | - |
| 工具库 | Lombok | - |
| 工具库 | Hutool | - |
| JSON处理 | FastJSON | - |
| API文档 | Swagger/Knife4j | - |
| 数据库 | MySQL | 5.7/8.0 |
| 缓存 | Redis | - |

---

## 使用说明

本规范作为项目开发的标准参考，所有代码提交前应确保符合以上规范。使用 `/crud` 命令生成的代码已自动遵循本规范。
