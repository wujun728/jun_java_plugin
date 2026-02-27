package io.github.wujun728.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.utils.CodeGenerator;
import io.github.wujun728.db.utils.CodeGenerator.ColumnInfo;
import io.github.wujun728.db.utils.CodeGenerator.TableInfo;
import org.junit.*;

import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.*;

/**
 * 代码生成器单元测试
 * 使用H2内存数据库，验证5种代码生成模式的完整性和正确性
 */
public class CodeGeneratorTest {

    private static DataSource dataSource;

    @BeforeClass
    public static void setup() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:h2:mem:codegen_testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        dataSource = ds;

        Db.init("codegen", dataSource);

        // 创建测试表：包含多种数据类型
        Db.use("codegen").execute("CREATE TABLE IF NOT EXISTS user_info (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "user_name VARCHAR(100), " +
                "age INT, " +
                "email VARCHAR(200), " +
                "salary DECIMAL(10,2), " +
                "is_active BOOLEAN DEFAULT TRUE, " +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");

        // 创建第二个测试表：无自增主键
        Db.use("codegen").execute("CREATE TABLE IF NOT EXISTS sys_config (" +
                "config_key VARCHAR(100) PRIMARY KEY, " +
                "config_value VARCHAR(500), " +
                "remark VARCHAR(200)" +
                ")");
    }

    @AfterClass
    public static void teardown() {
        Db.use("codegen").execute("DROP TABLE IF EXISTS user_info");
        Db.use("codegen").execute("DROP TABLE IF EXISTS sys_config");
    }

    // ==================== TableInfo 元数据测试 ====================

    @Test
    public void testGetTableInfo() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "USER_INFO", "com.test");
        assertNotNull(info);
        assertEquals("USER_INFO", info.getTableName());
        assertEquals("com.test", info.getPackageName());
        assertNotNull(info.getClassName());
        assertNotNull(info.getClassNameLower());
        assertNotNull(info.getDateStr());
        assertFalse(info.getColumns().isEmpty());
    }

    @Test
    public void testGetTableInfoColumns() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "USER_INFO", "com.test");
        List<ColumnInfo> columns = info.getColumns();
        assertTrue("应至少有7列", columns.size() >= 7);

        // 验证主键
        assertNotNull("应有主键列", info.getPkColumn());
        assertTrue("主键列应标记为pk", info.getPkColumn().isPk());
    }

    @Test
    public void testGetTableInfoTypeMapping() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "USER_INFO", "com.test");
        Map<String, String> typeMap = new HashMap<>();
        for (ColumnInfo col : info.getColumns()) {
            typeMap.put(col.getColumnName().toUpperCase(), col.getJavaType());
        }

        // H2的BIGINT → Long, VARCHAR → String, INT/INTEGER → Integer, DECIMAL → BigDecimal, BOOLEAN → Boolean, TIMESTAMP → Date
        assertEquals("Long", typeMap.get("ID"));
        assertEquals("String", typeMap.get("USER_NAME"));
        assertEquals("Integer", typeMap.get("AGE"));
        assertEquals("String", typeMap.get("EMAIL"));
        assertEquals("BigDecimal", typeMap.get("SALARY"));
        assertEquals("Boolean", typeMap.get("IS_ACTIVE"));
    }

    @Test
    public void testGetTableInfoFieldNameConversion() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "USER_INFO", "com.test");
        Map<String, ColumnInfo> colMap = new HashMap<>();
        for (ColumnInfo col : info.getColumns()) {
            colMap.put(col.getColumnName().toUpperCase(), col);
        }

        ColumnInfo userNameCol = colMap.get("USER_NAME");
        assertNotNull(userNameCol);
        assertEquals("userName", userNameCol.getFieldName());
        assertEquals("UserName", userNameCol.getFieldNameUpper());
        assertEquals("USER_NAME", userNameCol.getColumnNameUpper());
    }

    @Test
    public void testGetTableInfoStringPk() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "SYS_CONFIG", "com.test");
        assertNotNull(info.getPkColumn());
        assertEquals("String", info.getPkJavaType());
    }

    @Test
    public void testTableNameToClassName() {
        assertEquals("UserInfo", CodeGenerator.tableNameToClassName("user_info"));
        assertEquals("SysConfig", CodeGenerator.tableNameToClassName("sys_config"));
        assertEquals("User", CodeGenerator.tableNameToClassName("user"));
    }

    @Test
    public void testJdbcTypeMapping() {
        assertEquals("Integer", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.INTEGER));
        assertEquals("Long", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.BIGINT));
        assertEquals("String", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.VARCHAR));
        assertEquals("BigDecimal", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.DECIMAL));
        assertEquals("Date", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.TIMESTAMP));
        assertEquals("Boolean", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.BOOLEAN));
        assertEquals("Double", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.DOUBLE));
        assertEquals("Float", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.FLOAT));
        assertEquals("byte[]", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.BLOB));
        assertEquals("Object", CodeGenerator.jdbcTypeToJavaType(java.sql.Types.OTHER));
    }

    // ==================== SQL模式代码生成测试 ====================

    @Test
    public void testGeneratorCodeSQL() {
        Map<String, String> result = CodeGenerator.generatorCodeSQL(dataSource, "USER_INFO", "com.demo");
        assertEquals("应生成2个文件", 2, result.size());

        // 验证文件名
        assertTrue(result.containsKey("service/UserInfoService.java"));
        assertTrue(result.containsKey("controller/UserInfoController.java"));

        // 验证Service内容
        String service = result.get("service/UserInfoService.java");
        assertTrue(service.contains("package com.demo.service;"));
        assertTrue(service.contains("class UserInfoService"));
        assertTrue(service.contains("Db.insert("));
        assertTrue(service.contains("Db.execute("));
        assertTrue(service.contains("Db.queryList("));
        assertTrue(service.contains("Db.queryForMap("));
        assertTrue(service.contains("Db.queryForInt("));
        assertTrue(service.contains("Db.queryMapPages("));
        assertTrue(service.contains("USER_INFO"));

        // 验证Controller内容
        String controller = result.get("controller/UserInfoController.java");
        assertTrue(controller.contains("package com.demo.controller;"));
        assertTrue(controller.contains("class UserInfoController"));
        assertTrue(controller.contains("@RestController"));
        assertTrue(controller.contains("@RequestMapping"));
        assertTrue(controller.contains("@PostMapping"));
        assertTrue(controller.contains("@GetMapping"));
        assertTrue(controller.contains("@DeleteMapping"));
    }

    // ==================== Record模式代码生成测试 ====================

    @Test
    public void testGeneratorCodeRecord() {
        Map<String, String> result = CodeGenerator.generatorCodeRecord(dataSource, "USER_INFO", "com.demo");
        assertEquals("应生成2个文件", 2, result.size());

        assertTrue(result.containsKey("service/UserInfoService.java"));
        assertTrue(result.containsKey("controller/UserInfoController.java"));

        String service = result.get("service/UserInfoService.java");
        assertTrue(service.contains("package com.demo.service;"));
        assertTrue(service.contains("class UserInfoService"));
        assertTrue(service.contains("Db.save(TABLE,"));
        assertTrue(service.contains("Db.update(TABLE,"));
        assertTrue(service.contains("Db.deleteById(TABLE,"));
        assertTrue(service.contains("Db.findById(TABLE,"));
        assertTrue(service.contains("Db.findAll(TABLE)"));
        assertTrue(service.contains("Db.paginate("));
        assertTrue(service.contains("import io.github.wujun728.db.record.Record;"));

        String controller = result.get("controller/UserInfoController.java");
        assertTrue(controller.contains("Record record = new Record();"));
    }

    // ==================== Bean JPA模式代码生成测试 ====================

    @Test
    public void testGeneratorCodeSQLBeanJPA() {
        Map<String, String> result = CodeGenerator.generatorCodeSQLBeanJPA(dataSource, "USER_INFO", "com.demo");
        assertEquals("应生成3个文件", 3, result.size());

        assertTrue(result.containsKey("entity/UserInfo.java"));
        assertTrue(result.containsKey("service/UserInfoService.java"));
        assertTrue(result.containsKey("controller/UserInfoController.java"));

        // 验证JPA Entity
        String entity = result.get("entity/UserInfo.java");
        assertTrue(entity.contains("package com.demo.entity;"));
        assertTrue(entity.contains("import javax.persistence.*;"));
        assertTrue(entity.contains("@Table(name = \"USER_INFO\")"));
        assertTrue(entity.contains("class UserInfo implements Serializable"));
        assertTrue(entity.contains("@Id"));
        assertTrue(entity.contains("@Column(name ="));
        assertTrue(entity.contains("private Long"));
        assertTrue(entity.contains("private String"));

        // 验证Bean Service
        String service = result.get("service/UserInfoService.java");
        assertTrue(service.contains("Db.saveBean(entity)"));
        assertTrue(service.contains("Db.updateBean(entity)"));
        assertTrue(service.contains("Db.deleteBean(entity)"));
        assertTrue(service.contains("Db.findBeanById(UserInfo.class,"));
        assertTrue(service.contains("Db.findBeanList(UserInfo.class,"));
        assertTrue(service.contains("Db.findBeanPages(UserInfo.class,"));
    }

    // ==================== Bean MyBatis-Plus模式代码生成测试 ====================

    @Test
    public void testGeneratorCodeSQLBeanMybatis() {
        Map<String, String> result = CodeGenerator.generatorCodeSQLBeanMybatis(dataSource, "USER_INFO", "com.demo");
        assertEquals("应生成3个文件", 3, result.size());

        assertTrue(result.containsKey("entity/UserInfo.java"));
        assertTrue(result.containsKey("service/UserInfoService.java"));
        assertTrue(result.containsKey("controller/UserInfoController.java"));

        // 验证MyBatis-Plus Entity
        String entity = result.get("entity/UserInfo.java");
        assertTrue(entity.contains("package com.demo.entity;"));
        assertTrue(entity.contains("import com.baomidou.mybatisplus.annotation.*;"));
        assertTrue(entity.contains("@TableName(\"USER_INFO\")"));
        assertTrue(entity.contains("@TableId("));
        assertTrue(entity.contains("@TableField("));

        // Bean Service与JPA模式共用同一模板
        String service = result.get("service/UserInfoService.java");
        assertTrue(service.contains("Db.saveBean(entity)"));
    }

    // ==================== Model模式代码生成测试 ====================

    @Test
    public void testGeneratorCodeModel() {
        Map<String, String> result = CodeGenerator.generatorCodeModel(dataSource, "USER_INFO", "com.demo");
        assertEquals("应生成3个文件", 3, result.size());

        assertTrue(result.containsKey("model/UserInfo.java"));
        assertTrue(result.containsKey("service/UserInfoService.java"));
        assertTrue(result.containsKey("controller/UserInfoController.java"));

        // 验证Model类
        String model = result.get("model/UserInfo.java");
        assertTrue(model.contains("package com.demo.model;"));
        assertTrue(model.contains("import io.github.wujun728.db.record.Model;"));
        assertTrue(model.contains("extends Model<UserInfo>"));
        assertTrue(model.contains("public static final UserInfo dao = new UserInfo().dao();"));
        // 验证列名常量
        assertTrue(model.contains("public static final String"));
        // 验证便捷getter
        assertTrue(model.contains("return get("));

        // 验证Model Service
        String service = result.get("service/UserInfoService.java");
        assertTrue(service.contains("model.save()"));
        assertTrue(service.contains("model.update()"));
        assertTrue(service.contains("model.delete()"));
        assertTrue(service.contains("UserInfo.dao.findById("));
        assertTrue(service.contains("UserInfo.dao.findAll()"));
        assertTrue(service.contains("UserInfo.dao.paginate("));

        // 验证Model Controller
        String controller = result.get("controller/UserInfoController.java");
        assertTrue(controller.contains("model.put(data)"));
        assertTrue(controller.contains("model.getAttrs()"));
    }

    // ==================== 不同表结构测试 ====================

    @Test
    public void testGeneratorCodeWithStringPk() {
        Map<String, String> result = CodeGenerator.generatorCodeSQLBeanJPA(dataSource, "SYS_CONFIG", "com.demo");
        String entity = result.get("entity/SysConfig.java");
        assertNotNull(entity);
        assertTrue(entity.contains("class SysConfig"));
        assertTrue(entity.contains("private String"));

        String service = result.get("service/SysConfigService.java");
        assertTrue(service.contains("class SysConfigService"));
    }

    @Test
    public void testGeneratorCodeRecordForSysConfig() {
        Map<String, String> result = CodeGenerator.generatorCodeRecord(dataSource, "SYS_CONFIG", "com.sys");
        String service = result.get("service/SysConfigService.java");
        assertTrue(service.contains("package com.sys.service;"));
        assertTrue(service.contains("SYS_CONFIG"));
    }

    // ==================== Db静态方法代理测试 ====================

    @Test
    public void testDbStaticGeneratorMethods() {
        // 通过Db.use("codegen")调用
        Map<String, String> sqlResult = Db.use("codegen").generatorCodeSQL("USER_INFO", "com.test");
        assertNotNull(sqlResult);
        assertEquals(2, sqlResult.size());

        Map<String, String> recordResult = Db.use("codegen").generatorCodeRecord("USER_INFO", "com.test");
        assertNotNull(recordResult);
        assertEquals(2, recordResult.size());

        Map<String, String> jpaResult = Db.use("codegen").generatorCodeSQLBeanJPA("USER_INFO", "com.test");
        assertNotNull(jpaResult);
        assertEquals(3, jpaResult.size());

        Map<String, String> mybatisResult = Db.use("codegen").generatorCodeSQLBeanMybatis("USER_INFO", "com.test");
        assertNotNull(mybatisResult);
        assertEquals(3, mybatisResult.size());

        Map<String, String> modelResult = Db.use("codegen").generatorCodeModel("USER_INFO", "com.test");
        assertNotNull(modelResult);
        assertEquals(3, modelResult.size());
    }

    // ==================== 边界情况测试 ====================

    @Test
    public void testDifferentPackageNames() {
        Map<String, String> result1 = CodeGenerator.generatorCodeSQL(dataSource, "USER_INFO", "io.github.test");
        assertTrue(result1.get("service/UserInfoService.java").contains("package io.github.test.service;"));

        Map<String, String> result2 = CodeGenerator.generatorCodeSQL(dataSource, "USER_INFO", "com.company.app");
        assertTrue(result2.get("service/UserInfoService.java").contains("package com.company.app.service;"));
    }

    @Test
    public void testAllModesGenerateValidJavaCode() {
        String[] modes = {"SQL", "Record", "BeanJPA", "BeanMybatis", "Model"};

        for (String mode : modes) {
            Map<String, String> result;
            switch (mode) {
                case "SQL": result = CodeGenerator.generatorCodeSQL(dataSource, "USER_INFO", "com.test"); break;
                case "Record": result = CodeGenerator.generatorCodeRecord(dataSource, "USER_INFO", "com.test"); break;
                case "BeanJPA": result = CodeGenerator.generatorCodeSQLBeanJPA(dataSource, "USER_INFO", "com.test"); break;
                case "BeanMybatis": result = CodeGenerator.generatorCodeSQLBeanMybatis(dataSource, "USER_INFO", "com.test"); break;
                case "Model": result = CodeGenerator.generatorCodeModel(dataSource, "USER_INFO", "com.test"); break;
                default: continue;
            }

            for (Map.Entry<String, String> entry : result.entrySet()) {
                String fileName = entry.getKey();
                String code = entry.getValue();

                // 每个生成的文件都应该有package声明
                assertTrue(mode + " - " + fileName + " 缺少package声明", code.contains("package com.test."));
                // 每个生成的文件都应该有class声明
                assertTrue(mode + " - " + fileName + " 缺少class声明", code.contains("class "));
                // 不应有空白的模板变量残留
                assertFalse(mode + " - " + fileName + " 包含未替换的模板变量", code.contains("#("));
            }
        }
    }

    @Test
    public void testHasDateAndBigDecimalFlags() {
        TableInfo info = CodeGenerator.getTableInfo(dataSource, "USER_INFO", "com.test");
        // user_info表有TIMESTAMP和DECIMAL列
        assertTrue("应检测到Date类型", info.isHasDate());
        assertTrue("应检测到BigDecimal类型", info.isHasBigDecimal());

        TableInfo configInfo = CodeGenerator.getTableInfo(dataSource, "SYS_CONFIG", "com.test");
        // sys_config表只有VARCHAR列
        assertFalse("不应有Date类型", configInfo.isHasDate());
        assertFalse("不应有BigDecimal类型", configInfo.isHasBigDecimal());
    }

    @Test
    public void testEntityImportsConditional() {
        // user_info有Date和BigDecimal → 应有对应import
        Map<String, String> result1 = CodeGenerator.generatorCodeSQLBeanJPA(dataSource, "USER_INFO", "com.test");
        String entity1 = result1.get("entity/UserInfo.java");
        assertTrue(entity1.contains("import java.util.Date;"));
        assertTrue(entity1.contains("import java.math.BigDecimal;"));

        // sys_config无Date和BigDecimal → 不应有对应import
        Map<String, String> result2 = CodeGenerator.generatorCodeSQLBeanJPA(dataSource, "SYS_CONFIG", "com.test");
        String entity2 = result2.get("entity/SysConfig.java");
        assertFalse(entity2.contains("import java.util.Date;"));
        assertFalse(entity2.contains("import java.math.BigDecimal;"));
    }
}
