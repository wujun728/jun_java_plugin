package io.github.wujun728.sql;

import io.github.wujun728.sql.engine.DynamicSqlEngine;
import io.github.wujun728.sql.entity.ApiSql;
import io.github.wujun728.sql.entity.Result;
import io.github.wujun728.sql.utils.XmlParser;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * SQL 执行引擎模块单元测试
 */
public class SqlEngineTest {

    private final DynamicSqlEngine engine = new DynamicSqlEngine();

    @Test
    public void testDynamicSqlParseSimple() {
        String sql = "SELECT * FROM users WHERE id = #{id}";
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);

        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull("SqlMeta should not be null", sqlMeta);
        assertNotNull("SQL should not be null", sqlMeta.getSql());
        assertTrue("SQL should contain parameter placeholder", sqlMeta.getSql().contains("?"));
        assertNotNull("JDBC params should not be null", sqlMeta.getJdbcParamValues());
        assertEquals(1, sqlMeta.getJdbcParamValues().size());
        assertEquals(1, sqlMeta.getJdbcParamValues().get(0));
    }

    @Test
    public void testDynamicSqlParseIfCondition() {
        String sql = "SELECT * FROM users WHERE 1=1 "
                + "<if test=\"name != null\"> AND name = #{name} </if>"
                + "<if test=\"age != null\"> AND age = #{age} </if>";

        // Only name provided
        Map<String, Object> params = new HashMap<>();
        params.put("name", "test");
        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        assertTrue("SQL should contain name condition", sqlMeta.getSql().contains("name"));
        assertFalse("SQL should not contain age condition", sqlMeta.getSql().contains("age"));

        // Both provided
        params.put("age", 25);
        sqlMeta = engine.parse(sql, params);
        assertTrue("SQL should contain both conditions",
                sqlMeta.getSql().contains("name") && sqlMeta.getSql().contains("age"));
    }

    @Test
    public void testDynamicSqlParseWhereTag() {
        String sql = "SELECT * FROM users "
                + "<where>"
                + "<if test=\"name != null\"> AND name = #{name} </if>"
                + "<if test=\"age != null\"> AND age = #{age} </if>"
                + "</where>";

        Map<String, Object> params = new HashMap<>();
        params.put("name", "test");
        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        String parsedSql = sqlMeta.getSql().toUpperCase();
        assertTrue("SQL should contain WHERE", parsedSql.contains("WHERE"));
    }

    @Test
    public void testDynamicSqlParseForeach() {
        String sql = "SELECT * FROM users WHERE id IN "
                + "<foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"
                + "#{id}"
                + "</foreach>";

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(1, 2, 3));
        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        assertTrue("Should have 3 parameters", sqlMeta.getJdbcParamValues().size() == 3);
    }

    @Test
    public void testDynamicSqlParseSetTag() {
        String sql = "UPDATE users "
                + "<set>"
                + "<if test=\"name != null\"> name = #{name}, </if>"
                + "<if test=\"age != null\"> age = #{age}, </if>"
                + "</set>"
                + " WHERE id = #{id}";

        Map<String, Object> params = new HashMap<>();
        params.put("name", "newName");
        params.put("id", 1);
        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        String parsedSql = sqlMeta.getSql().toUpperCase();
        assertTrue("SQL should contain SET", parsedSql.contains("SET"));
        assertTrue("SQL should contain name", sqlMeta.getSql().contains("name"));
    }

    @Test
    public void testDynamicSqlParseTrimTag() {
        String sql = "SELECT * FROM users "
                + "<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">"
                + "<if test=\"name != null\"> AND name = #{name} </if>"
                + "</trim>";

        Map<String, Object> params = new HashMap<>();
        params.put("name", "test");
        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        String parsedSql = sqlMeta.getSql().toUpperCase();
        assertTrue("SQL should contain WHERE", parsedSql.contains("WHERE"));
    }

    @Test
    public void testDynamicSqlParseNoParams() {
        String sql = "SELECT * FROM users WHERE status = 1";
        Map<String, Object> params = new HashMap<>();

        SqlMeta sqlMeta = engine.parse(sql, params);
        assertNotNull(sqlMeta);
        assertTrue("Should have no JDBC parameters", sqlMeta.getJdbcParamValues().isEmpty());
    }

    @Test
    public void testDynamicSqlParseParameter() {
        String sql = "SELECT * FROM users WHERE name = #{name} AND age > #{minAge}";
        Set<String> paramNames = engine.parseParameter(sql);
        assertNotNull(paramNames);
        assertTrue("Should contain 'name'", paramNames.contains("name"));
        assertTrue("Should contain 'minAge'", paramNames.contains("minAge"));
    }

    @Test
    public void testXmlParserParseSql() throws Exception {
        String xml = "<sqls>\n"
                + "  <defaultDB>main</defaultDB>\n"
                + "  <sql id=\"findUser\" db=\"main\">\n"
                + "    SELECT * FROM users WHERE id = #{id}\n"
                + "  </sql>\n"
                + "  <sql id=\"listUsers\" db=\"main\">\n"
                + "    SELECT * FROM users\n"
                + "  </sql>\n"
                + "</sqls>";

        Map<String, ApiSql> sqlMap = XmlParser.parseSql(xml);
        assertNotNull("SQL map should not be null", sqlMap);
        assertEquals("Should have 2 SQLs", 2, sqlMap.size());
        assertTrue("Should contain findUser", sqlMap.containsKey("findUser"));
        assertTrue("Should contain listUsers", sqlMap.containsKey("listUsers"));

        ApiSql findUser = sqlMap.get("findUser");
        assertNotNull(findUser.getText());
        assertTrue(findUser.getText().contains("SELECT"));
        assertEquals("main", findUser.getDatasourceId());
    }

    @Test
    public void testResultStatusCodeSync() {
        // Verify that status and code are always in sync
        Result success = new Result(0, "ok", "data");
        assertEquals(success.get("code"), success.get("status"));

        Result error = new Result(500, "error", null);
        assertEquals(error.get("code"), error.get("status"));

        Result custom = new Result(404, "not found");
        assertEquals(custom.get("code"), custom.get("status"));
    }

    @Test
    public void testResultFactoryMethods() {
        Result ok = Result.ok();
        assertEquals(0, ok.get("code"));

        Result okData = Result.ok("myData");
        assertEquals(0, okData.get("code"));
        assertEquals("myData", okData.get("data"));

        Result error = Result.error();
        assertEquals(500, error.get("code"));

        Result errorMsg = Result.error("bad");
        assertEquals(500, errorMsg.get("code"));
        assertEquals("bad", errorMsg.get("msg"));

        Result errorCode = Result.error(403, "forbidden");
        assertEquals(403, errorCode.get("code"));
        assertEquals(403, errorCode.get("status"));
    }
}
