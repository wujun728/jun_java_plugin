---
name: crud
description: 一键生成完整业务模块代码（SQL/Entity/DAO/XML/Repository/Service/Controller/Mapper/POJO），用于创建新的业务功能
---

# crud - 一键生成完整业务模块

根据用户提供的需求描述，一键生成完整的业务模块代码，包括：SQL建表语句 -> MyBatis XML -> Entity -> DAO -> Repository -> Service -> Controller -> MapStruct Mapper -> POJO (DTO/VO/QO)。

## 自动触发条件

当用户的输入符合以下任一情况时，应自动应用此技能（无需用户明确输入 /crud）：

### 关键词触发
- 包含"创建"、"新建"、"添加"、"生成"等动词 + "功能"、"模块"、"管理"、"表"等名词
- 包含"XXX管理"模式，如"用户管理"、"订单管理"、"商品管理"
- 包含"帮我做一个"、"帮我写一个"、"实现一个"等请求模式
- 包含字段描述列表（多个字段说明）

### 场景触发
- 用户描述一个新的业务实体及其属性
- 用户提供数据库表结构或字段清单
- 用户需要实现增删改查（CRUD）功能
- 用户描述一个需要持久化存储的业务对象

### 示例输入（应自动触发）
- "帮我创建一个商品分类管理功能"
- "我需要一个用户管理模块"
- "做一个订单表，包含订单号、金额、状态等字段"
- "实现一个会员卡功能，需要存储卡号、余额、积分"
- "新建一个评价管理，包含评分、评价内容、图片"

---

## 执行步骤

### 步骤1：项目环境检测

在生成代码之前，**必须先完成以下检测**：

#### 1.1 检测项目包名（Package）

**检测方法：**
1. 查找 `src/main/java` 目录下的Java文件
2. 读取已有Java文件的 `package` 声明
3. 分析项目的根包名结构

**处理规则：**
- **如果检测到已有代码**：使用项目中已存在的包名结构
- **如果是空项目或无法检测**：询问用户确认包名

**询问示例：**
```
检测到这是一个新项目，请确认项目的基础包名：
- 例如：com.example.demo
- 例如：net.company.project
```

#### 1.2 检测代码作者（Author）

**检测方法：**
1. 查找项目中已有的 `@author` 注释
2. 检查 git 配置中的用户名

**处理规则：**
- **如果检测到统一的作者名**：使用检测到的作者名
- **如果未检测到或不统一**：询问用户确认作者名

**询问示例：**
```
请确认代码注释中的作者名（@author）：
- 可以是您的名字、工号或团队名称
- 例如：zhangsan、张三、dev-team
```

---

### 步骤2：需求分析

- 分析用户的业务需求描述
- 确定需要创建的表名（遵循 `s_` 前缀命名规范）
- 确定表的字段、类型、注释
- 确定业务模块名称（用于包路径）

---

### 步骤3：按顺序生成代码

按以下顺序依次生成：

1. **SQL建表语句** - 创建数据库表
2. **Entity实体类** - 映射数据库表
3. **MyBatis XML** - SQL映射文件
4. **DAO接口** - 数据访问接口
5. **Repository** - 数据仓储层
6. **POJO** - 数据传输对象 (DTO/VO/QO)
7. **MapStruct Mapper** - 对象转换器
8. **Service** - 业务逻辑层
9. **Controller** - 接口控制层

---

## 代码规范要求（阿里巴巴Java开发手册）

### 命名规范
- **类名**：使用 UpperCamelCase 风格
- **方法名、参数名、成员变量、局部变量**：使用 lowerCamelCase 风格
- **常量**：全部大写，单词间用下划线隔开
- **包名**：全部小写
- **抽象类**：以 Abstract 或 Base 开头
- **异常类**：以 Exception 结尾
- **测试类**：以 Test 结尾

### 数据库规范
- **表名**：使用 `s_` 前缀 + 蛇形命名，如 `s_user_info`
- **字段名**：蛇形命名，如 `create_time`
- **主键**：统一使用 `id`，类型为 `BIGINT`
- **必备字段**：`id`、`tenant_id`、`create_time`、`update_time`、`is_deleted`

### 分层规范
- **Controller**：只做参数校验和结果返回，不写业务逻辑
- **Service**：业务逻辑处理，事务控制
- **Repository**：数据访问封装，异常处理
- **DAO**：MyBatis Mapper接口，纯数据访问

### 注释规范
- 类、方法必须有 Javadoc 注释
- 字段使用 `@ApiModelProperty` 注解说明
- **@author 使用检测到的作者名或用户确认的作者名**

---

## 项目结构参考

使用 `{basePackage}` 表示检测到的或用户确认的基础包名：

```
{basePackage}
├── domain
│   ├── dao/{实体名}Dao.java              # MyBatis Mapper接口
│   ├── entity/{实体名}.java              # 数据库实体
│   └── pojo/{模块名}/                    # 业务对象
│       ├── {实体名}DTO.java              # 数据传输对象
│       ├── {实体名}VO.java               # 视图对象
│       └── {实体名}SearchQO.java         # 查询对象
├── mapping/{模块名}/{实体名}Mapper.java   # MapStruct映射器
├── repository/{模块名}/{实体名}Repository.java  # 数据仓储
├── service/{模块名}/{实体名}Service.java  # 业务服务
└── web/admin/{模块名}/{实体名}Controller.java   # 控制器
```

---

## 代码模板

> **注意**：以下模板中的 `{basePackage}` 和 `{author}` 为动态变量，需要在生成前通过检测或询问用户确定。

### Entity 模板
```java
package {basePackage}.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * {实体描述} 实体类
 *
 * @author {author}
 */
@Data
public class {实体名} {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("商户ID")
    private Long tenantId;

    // 业务字段...

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除 0否 1是")
    private Integer isDeleted;
}
```

### DAO 模板
```java
package {basePackage}.domain.dao;

import java.util.List;
import {basePackage}.domain.entity.{实体名};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * {实体描述} Mapper接口
 *
 * @author {author}
 */
@Mapper
public interface {实体名}Dao {

    int insert({实体名} row);

    int insertSelective({实体名} row);

    int batchInsert(@Param(value = "rows") List<{实体名}> rows);

    {实体名} selectByPrimaryKey(Long id);

    List<{实体名}> selectByIds(@Param(value = "ids") List<Long> ids);

    int updateByPrimaryKeySelective({实体名} row);

    int deleteByPrimaryKey(Long id);

    int removeByPrimaryKey(Long id);

    List<{实体名}> selectByQo(@Param(value = "qo") {实体名}SearchQO qo);
}
```

### Repository 模板
```java
package {basePackage}.repository.{模块名};

import lombok.RequiredArgsConstructor;
import net.trueland.framework.core.ServiceException;
import {basePackage}.common.ErrorCode;
import {basePackage}.domain.dao.{实体名}Dao;
import {basePackage}.domain.entity.{实体名};
import {basePackage}.domain.pojo.{模块名}.{实体名}SearchQO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * {实体描述} Repository
 *
 * @author {author}
 */
@Repository
@RequiredArgsConstructor
public class {实体名}Repository {

    private final {实体名}Dao {实体名小写}Dao;

    /**
     * 新增
     */
    public void addEntity({实体名} row) {
        {实体名小写}Dao.insertSelective(row);
    }

    /**
     * 编辑
     */
    public void updateEntity({实体名} row) {
        row.setUpdateTime(LocalDateTime.now());
        {实体名小写}Dao.updateByPrimaryKeySelective(row);
    }

    /**
     * 根据id查找数据(不存在则抛异常)
     */
    public {实体名} exist(Long id) {
        {实体名} data = {实体名小写}Dao.selectByPrimaryKey(id);
        if (Objects.isNull(data)) {
            throw new ServiceException(ErrorCode.DATA_NOT_EXISTS);
        }
        return data;
    }

    /**
     * 根据id查找数据
     */
    public {实体名} getById(Long id) {
        return {实体名小写}Dao.selectByPrimaryKey(id);
    }

    /**
     * 根据ids批量查询
     */
    public List<{实体名}> selectByIds(List<Long> ids) {
        return {实体名小写}Dao.selectByIds(ids);
    }

    /**
     * 条件查询列表
     */
    public List<{实体名}> getList({实体名}SearchQO qo) {
        return {实体名小写}Dao.selectByQo(qo);
    }

    /**
     * 逻辑删除
     */
    public void removeById(Long id) {
        {实体名小写}Dao.removeByPrimaryKey(id);
    }
}
```

### Service 模板
```java
package {basePackage}.service.{模块名};

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import {basePackage}.domain.entity.{实体名};
import {basePackage}.domain.pojo.{模块名}.{实体名}DTO;
import {basePackage}.domain.pojo.{模块名}.{实体名}VO;
import {basePackage}.domain.pojo.{模块名}.{实体名}SearchQO;
import {basePackage}.mapping.{模块名}.{实体名}Mapper;
import {basePackage}.repository.{模块名}.{实体名}Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {实体描述} Service
 *
 * @author {author}
 */
@Service
@AllArgsConstructor
@Slf4j
public class {实体名}Service {

    private final {实体名}Mapper {实体名小写}Mapper;
    private final {实体名}Repository {实体名小写}Repository;

    /**
     * 新增
     */
    public Long add({实体名}DTO dto) {
        {实体名} entity = {实体名小写}Mapper.toEntity(dto);
        {实体名小写}Repository.addEntity(entity);
        return entity.getId();
    }

    /**
     * 编辑
     */
    public void update(Long id, {实体名}DTO dto) {
        {实体名} entity = {实体名小写}Repository.exist(id);
        {实体名小写}Mapper.updateEntity(dto, entity);
        {实体名小写}Repository.updateEntity(entity);
    }

    /**
     * 详情
     */
    public {实体名}VO getInfo(Long id) {
        {实体名} entity = {实体名小写}Repository.exist(id);
        return {实体名小写}Mapper.toVO(entity);
    }

    /**
     * 列表查询
     */
    public List<{实体名}VO> getList({实体名}SearchQO qo) {
        List<{实体名}> list = {实体名小写}Repository.getList(qo);
        return {实体名小写}Mapper.toVOList(list);
    }

    /**
     * 删除
     */
    public void removeById(Long id) {
        {实体名小写}Repository.exist(id);
        {实体名小写}Repository.removeById(id);
    }
}
```

### Controller 模板
```java
package {basePackage}.web.admin.{模块名};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.trueland.framework.web.result.ApiResult;
import {basePackage}.domain.pojo.{模块名}.{实体名}DTO;
import {basePackage}.domain.pojo.{模块名}.{实体名}VO;
import {basePackage}.domain.pojo.{模块名}.{实体名}SearchQO;
import {basePackage}.service.{模块名}.{实体名}Service;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {实体描述} Controller
 *
 * @author {author}
 */
@Api(tags = "{实体描述}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/{模块路径}")
public class {实体名}Controller {

    private final {实体名}Service {实体名小写}Service;

    @PostMapping
    @ApiOperation(value = "新增")
    public ApiResult<Long> add(@Validated @RequestBody {实体名}DTO dto) {
        return ApiResult.ok({实体名小写}Service.add(dto));
    }

    @PostMapping("{id}")
    @ApiOperation(value = "编辑")
    public ApiResult<Void> update(@PathVariable Long id, @Validated @RequestBody {实体名}DTO dto) {
        {实体名小写}Service.update(id, dto);
        return ApiResult.ok();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "详情")
    public ApiResult<{实体名}VO> getInfo(@PathVariable Long id) {
        return ApiResult.ok({实体名小写}Service.getInfo(id));
    }

    @GetMapping
    @ApiOperation(value = "列表查询")
    public ApiResult<List<{实体名}VO>> getList(@SpringQueryMap {实体名}SearchQO qo) {
        return ApiResult.ok({实体名小写}Service.getList(qo));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除")
    public ApiResult<Void> remove(@PathVariable Long id) {
        {实体名小写}Service.removeById(id);
        return ApiResult.ok();
    }
}
```

### MapStruct Mapper 模板
```java
package {basePackage}.mapping.{模块名};

import {basePackage}.domain.entity.{实体名};
import {basePackage}.domain.pojo.{模块名}.{实体名}DTO;
import {basePackage}.domain.pojo.{模块名}.{实体名}VO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * {实体描述} MapStruct映射器
 *
 * @author {author}
 */
@Mapper(componentModel = "spring")
public interface {实体名}Mapper {

    {实体名} toEntity({实体名}DTO dto);

    void updateEntity({实体名}DTO dto, @MappingTarget {实体名} entity);

    {实体名}VO toVO({实体名} entity);

    List<{实体名}VO> toVOList(List<{实体名}> list);
}
```

---

## 生成文件清单

生成完成后，请列出所有生成的文件路径（使用检测到的 `{basePackage}` 转换为路径）：

- SQL文件（建议保存位置）
- Entity: `src/main/java/{basePackagePath}/domain/entity/{实体名}.java`
- DAO: `src/main/java/{basePackagePath}/domain/dao/{实体名}Dao.java`
- XML: `src/main/resources/mybatis/mapper/{实体名}Dao.xml`
- Repository: `src/main/java/{basePackagePath}/repository/{模块名}/{实体名}Repository.java`
- POJO: `src/main/java/{basePackagePath}/domain/pojo/{模块名}/`
- Mapper: `src/main/java/{basePackagePath}/mapping/{模块名}/{实体名}Mapper.java`
- Service: `src/main/java/{basePackagePath}/service/{模块名}/{实体名}Service.java`
- Controller: `src/main/java/{basePackagePath}/web/admin/{模块名}/{实体名}Controller.java`

---

## 变量说明

| 变量 | 说明 | 获取方式 |
|-----|------|---------|
| `{basePackage}` | 项目基础包名 | 检测项目或询问用户 |
| `{basePackagePath}` | 包名转路径 | 将 `.` 替换为 `/` |
| `{author}` | 代码作者 | 检测项目或询问用户 |
| `{实体名}` | 实体类名（大驼峰） | 根据需求分析 |
| `{实体名小写}` | 实体变量名（小驼峰） | 首字母小写 |
| `{实体描述}` | 实体中文描述 | 根据需求分析 |
| `{模块名}` | 模块包名（小写） | 根据需求分析 |
| `{模块路径}` | API路径（短横线分隔） | 根据模块名转换 |
