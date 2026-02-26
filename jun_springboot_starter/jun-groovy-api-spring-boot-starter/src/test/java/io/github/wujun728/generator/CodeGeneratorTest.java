package io.github.wujun728.generator;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.generator.entity.FieldInfo;
import io.github.wujun728.generator.entity.NonCaseString;
import io.github.wujun728.generator.entity.ParamInfo;
import io.github.wujun728.generator.util.FreemarkerUtil;
import io.github.wujun728.generator.util.TableParseUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.*;

/**
 * 代码生成模块单元测试
 */
public class CodeGeneratorTest {

    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:test_codegen;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.init();
        this.dataSource = ds;

        try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS sys_user ("
                    + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + "user_name VARCHAR(100),"
                    + "email VARCHAR(200),"
                    + "age INT,"
                    + "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")");
            stmt.execute("COMMENT ON TABLE sys_user IS 'user table'");
            stmt.execute("COMMENT ON COLUMN sys_user.id IS 'user ID'");
            stmt.execute("COMMENT ON COLUMN sys_user.user_name IS 'username'");
            stmt.execute("COMMENT ON COLUMN sys_user.email IS 'email address'");
            stmt.execute("COMMENT ON COLUMN sys_user.age IS 'user age'");
            stmt.execute("COMMENT ON COLUMN sys_user.create_time IS 'create time'");
            stmt.execute("INSERT INTO sys_user (user_name, email, age) VALUES ('test', 'test@test.com', 25)");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (dataSource instanceof DruidDataSource) {
            ((DruidDataSource) dataSource).close();
        }
    }

    @Test
    public void testGetClassInfo() {
        ClassInfo classInfo = CodeUtil.getClassInfo(dataSource, "SYS_USER");
        assertNotNull("ClassInfo should not be null", classInfo);
        assertNotNull(classInfo.getFieldList());
        assertTrue("Should have fields", classInfo.getFieldList().size() >= 4);

        // Verify field mapping
        boolean foundId = false;
        boolean foundUserName = false;
        for (FieldInfo field : classInfo.getFieldList()) {
            if ("ID".equalsIgnoreCase(field.getColumnName())) {
                foundId = true;
                assertTrue("id should be primary key", field.getIsPrimaryKey());
            }
            if ("USER_NAME".equalsIgnoreCase(field.getColumnName())) {
                foundUserName = true;
            }
        }
        assertTrue("Should have id field", foundId);
        assertTrue("Should have user_name field", foundUserName);
    }

    @Test
    public void testGenCodeDbRecord() {
        Map<String, String> codeMap = CodeUtil.genCodeReturnMap(dataSource, "SYS_USER", CodeUtil.GROUP_DB_RECORD);
        assertNotNull("Code map should not be null", codeMap);
        assertFalse("Code map should not be empty", codeMap.isEmpty());

        // DB_RECORD group has controller and entity templates
        for (Map.Entry<String, String> entry : codeMap.entrySet()) {
            assertNotNull("Code content should not be null for " + entry.getKey(), entry.getValue());
            assertTrue("Code content should not be empty for " + entry.getKey(), entry.getValue().length() > 0);
        }
    }

    @Test
    public void testGenCodeForTables() {
        List<String> tables = Arrays.asList("SYS_USER");
        Map<String, Map<String, String>> result = CodeUtil.genCodeForTables(dataSource, tables, CodeUtil.GROUP_DB_RECORD);
        assertNotNull(result);
        assertTrue("Should contain SYS_USER", result.containsKey("SYS_USER"));

        Map<String, String> sysUserCode = result.get("SYS_USER");
        assertFalse("sys_user code should not be empty", sysUserCode.isEmpty());
    }

    @Test
    public void testFreemarkerUtil() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "World");
        String result = FreemarkerUtil.processStringTemplate("hello ${name}", params);
        assertEquals("hello World", result);
    }

    @Test
    public void testFreemarkerUtilGenTemplateStr() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("count", 42);
        params.put("label", "items");
        String template = "Total: ${count} ${label}";
        String result = FreemarkerUtil.genTemplateStr(params, "test", template);
        assertEquals("Total: 42 items", result);
    }

    @Test
    public void testTableParseUtil() {
        String ddl = "CREATE TABLE `test_table` (\n"
                + "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n"
                + "  `name` varchar(100) DEFAULT NULL COMMENT 'Name',\n"
                + "  `status` int(11) DEFAULT NULL COMMENT 'Status',\n"
                + "  PRIMARY KEY (`id`)\n"
                + ") ENGINE=InnoDB COMMENT='test table';";

        ParamInfo paramInfo = new ParamInfo();
        paramInfo.setTableSql(ddl);
        Map<String, Object> options = new HashMap<>();
        options.put("nameCaseType", ParamInfo.NAME_CASE_TYPE.CAMEL_CASE);
        options.put("isPackageType", false);
        paramInfo.setOptions(options);

        try {
            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(paramInfo);
            assertNotNull("ClassInfo should not be null", classInfo);
            assertEquals("TestTable", classInfo.getClassName());
            assertNotNull(classInfo.getFieldList());
            assertTrue("Should have at least 3 fields", classInfo.getFieldList().size() >= 3);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testCodeUtilGetType() {
        assertEquals("java.lang.Integer", CodeUtil.getType(4));
        assertEquals("java.lang.Long", CodeUtil.getType(-5));
        assertEquals("java.lang.String", CodeUtil.getType(12));
        assertEquals("java.util.Date", CodeUtil.getType(93));
        assertEquals("java.lang.Boolean", CodeUtil.getType(16));
        assertEquals("java.lang.String", CodeUtil.getType(9999)); // default
    }

    @Test
    public void testCodeUtilReplace_() {
        assertEquals("userName", CodeUtil.replace_("user_name"));
        assertEquals("id", CodeUtil.replace_("id"));
        assertEquals("createTime", CodeUtil.replace_("create_time"));
    }

    @Test
    public void testCodeUtilFirstUpper() {
        assertEquals("Hello", CodeUtil.firstUpper("hello"));
        assertEquals("A", CodeUtil.firstUpper("a"));
        assertEquals("", CodeUtil.firstUpper(""));
    }

    @Test
    public void testCodeUtilSimpleName() {
        assertEquals("String", CodeUtil.simpleName("java.lang.String"));
        assertEquals("Integer", CodeUtil.simpleName("java.lang.Integer"));
        assertEquals("Date", CodeUtil.simpleName("java.util.Date"));
    }
}
