package io.github.wujun728.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.db.record.*;
import io.github.wujun728.db.record.dialect.*;
import io.github.wujun728.db.record.mapper.BatchSql;
import io.github.wujun728.db.utils.RecordUtil;
import org.junit.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * 并发安全、方言SQL生成、边界情况的综合测试
 * 覆盖：
 * 1. 多线程并发初始化 DbPro（C1修复验证）
 * 2. 多线程并发CRUD操作
 * 3. 所有Dialect的forDbUpdate排除主键列（H4修复验证）
 * 4. Record类型转换边界情况（H3修复验证）
 * 5. RecordUtil反射缓存及转换边界（M3修复验证）
 * 6. DbException异常传播（M10修复验证）
 * 7. 空结果/null参数边界
 * 8. Model缓存字段验证（M7修复验证）
 */
public class ConcurrencyAndEdgeCaseTest {

    private static DataSource dataSource;

    @BeforeClass
    public static void setup() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:h2:mem:concurrency_test;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        dataSource = ds;

        Db.init(dataSource);

        Db.execute("CREATE TABLE IF NOT EXISTS user_info (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "user_name VARCHAR(100), " +
                "age INT, " +
                "email VARCHAR(200), " +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");

        Db.execute("CREATE TABLE IF NOT EXISTS sys_config (" +
                "config_key VARCHAR(100) PRIMARY KEY, " +
                "config_value VARCHAR(500)" +
                ")");
    }

    @Before
    public void beforeEach() {
        Db.execute("DELETE FROM user_info");
        Db.execute("DELETE FROM sys_config");
        Db.execute("ALTER TABLE user_info ALTER COLUMN id RESTART WITH 1");
    }

    @AfterClass
    public static void teardown() {
        Db.execute("DROP TABLE IF EXISTS user_info");
        Db.execute("DROP TABLE IF EXISTS sys_config");
    }

    // ==================== 1. 并发初始化测试 ====================

    @Test
    public void testConcurrentDbProInit() throws Exception {
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        Set<DbPro> instances = Collections.newSetFromMap(new ConcurrentHashMap<>());

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    DruidDataSource ds = new DruidDataSource();
                    ds.setUrl("jdbc:h2:mem:concurrent_init_" + idx + ";DB_CLOSE_DELAY=-1;MODE=MySQL");
                    ds.setUsername("sa");
                    ds.setPassword("");
                    ds.setDriverClassName("org.h2.Driver");

                    // 多线程同时初始化同名数据源
                    Db.init("concurrent_test", ds);
                    DbPro dbPro = Db.use("concurrent_test");
                    instances.add(dbPro);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    // 不应抛异常
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(threadCount, successCount.get());
        // 所有线程获取的应是同一个 DbPro 实例（第一个成功初始化的）
        assertEquals(1, instances.size());
    }

    @Test
    public void testConcurrentForceInit() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    DruidDataSource ds = new DruidDataSource();
                    ds.setUrl("jdbc:h2:mem:force_init_test;DB_CLOSE_DELAY=-1;MODE=MySQL");
                    ds.setUsername("sa");
                    ds.setPassword("");
                    ds.setDriverClassName("org.h2.Driver");

                    // force=true 强制重新初始化
                    Db.init("force_test", ds, true);
                    assertNotNull(Db.use("force_test"));
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(threadCount, successCount.get());
    }

    // ==================== 2. 并发CRUD测试 ====================

    @Test
    public void testConcurrentInsert() throws Exception {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    Db.execute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                            new Object[]{"并发用户" + idx, 20 + idx, "concurrent" + idx + "@test.com"});
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(threadCount, successCount.get());
        assertEquals(threadCount, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testConcurrentRecordSaveAndFind() throws Exception {
        int threadCount = 30;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger saveSuccess = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    Record record = new Record();
                    record.set("user_name", "record_user_" + idx);
                    record.set("age", 20 + idx);
                    record.set("email", "rec" + idx + "@test.com");
                    if (Db.save("user_info", "id", record)) {
                        saveSuccess.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(threadCount, saveSuccess.get());
        List<Record> all = Db.findAll("user_info");
        assertEquals(threadCount, all.size());
    }

    @Test
    public void testConcurrentReadWrite() throws Exception {
        // 先插入一些初始数据
        for (int i = 1; i <= 10; i++) {
            Db.execute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                    new Object[]{"初始用户" + i, 20 + i, "init" + i + "@test.com"});
        }

        int threadCount = 30;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    if (idx % 3 == 0) {
                        // 读操作
                        List<Record> records = Db.findAll("user_info");
                        assertNotNull(records);
                    } else if (idx % 3 == 1) {
                        // 写操作
                        Db.execute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                                new Object[]{"新增用户" + idx, 30 + idx, "new" + idx + "@test.com"});
                    } else {
                        // 查询操作
                        int count = Db.queryForInt("SELECT count(*) FROM user_info");
                        assertTrue(count >= 10);
                    }
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(0, errorCount.get());
    }

    // ==================== 3. Dialect forDbUpdate 排除主键列测试 ====================

    @Test
    public void testMysqlDialectForDbUpdateExcludesPK() {
        MysqlDialect dialect = new MysqlDialect();
        Record record = new Record();
        record.set("id", 1L);
        record.set("user_name", "张三");
        record.set("age", 25);

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate("user_info", new String[]{"id"}, new Object[]{1L}, record, sql, paras);

        String sqlStr = sql.toString();
        // SET子句中不应包含 `id`
        int setIdx = sqlStr.indexOf(" set ");
        int whereIdx = sqlStr.indexOf(" where ");
        String setClause = sqlStr.substring(setIdx, whereIdx);
        assertFalse(setClause.contains("`id` = ?"));
        assertTrue(setClause.contains("`user_name` = ?"));
        assertTrue(setClause.contains("`age` = ?"));
        // paras: 2个SET值 + 1个WHERE条件值(id)
        assertEquals(3, paras.size());
        // 最后一个参数一定是WHERE中的id值
        assertEquals(1L, paras.get(paras.size() - 1));
        // SET值中应包含"张三"和25（顺序不确定，HashMap无序）
        assertTrue(paras.subList(0, 2).contains("张三"));
        assertTrue(paras.subList(0, 2).contains(25));
    }

    @Test
    public void testOracleDialectForDbUpdateExcludesPK() {
        OracleDialect dialect = new OracleDialect();
        Record record = new Record();
        record.set("ID", 1L);
        record.set("USER_NAME", "张三");
        record.set("AGE", 25);

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate("USER_INFO", new String[]{"ID"}, new Object[]{1L}, record, sql, paras);

        String sqlStr = sql.toString();
        // ID不应在SET子句中
        int setIdx = sqlStr.indexOf(" set ");
        int whereIdx = sqlStr.indexOf(" where ");
        String setClause = sqlStr.substring(setIdx, whereIdx);
        assertFalse(setClause.contains("ID = ?"));
        assertTrue(setClause.contains("USER_NAME = ?"));
        assertTrue(setClause.contains("AGE = ?"));
        assertEquals(3, paras.size());
    }

    @Test
    public void testPostgreSqlDialectForDbUpdateExcludesPK() {
        PostgreSqlDialect dialect = new PostgreSqlDialect();
        Record record = new Record();
        record.set("id", 1L);
        record.set("user_name", "张三");
        record.set("age", 25);

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate("user_info", new String[]{"id"}, new Object[]{1L}, record, sql, paras);

        String sqlStr = sql.toString();
        int setIdx = sqlStr.indexOf(" set ");
        int whereIdx = sqlStr.indexOf(" where ");
        String setClause = sqlStr.substring(setIdx, whereIdx);
        assertFalse(setClause.contains("\"id\" = ?"));
        assertTrue(setClause.contains("\"user_name\" = ?"));
        assertTrue(setClause.contains("\"age\" = ?"));
        assertEquals(3, paras.size());
    }

    @Test
    public void testSqlite3DialectForDbUpdateExcludesPK() {
        Sqlite3Dialect dialect = new Sqlite3Dialect();
        Record record = new Record();
        record.set("id", 1L);
        record.set("user_name", "张三");
        record.set("age", 25);

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate("user_info", new String[]{"id"}, new Object[]{1L}, record, sql, paras);

        String sqlStr = sql.toString();
        int setIdx = sqlStr.indexOf(" set ");
        int whereIdx = sqlStr.indexOf(" where ");
        String setClause = sqlStr.substring(setIdx, whereIdx);
        assertFalse(setClause.contains("id = ?"));
        assertTrue(setClause.contains("user_name = ?"));
        assertTrue(setClause.contains("age = ?"));
        assertEquals(3, paras.size());
    }

    @Test
    public void testDialectForDbUpdateCompositePK() {
        MysqlDialect dialect = new MysqlDialect();
        Record record = new Record();
        record.set("user_id", 1L);
        record.set("role_id", 2L);
        record.set("permission", "admin");

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate("user_role", new String[]{"user_id", "role_id"},
                new Object[]{1L, 2L}, record, sql, paras);

        String sqlStr = sql.toString();
        // SET中只有permission
        int setIdx = sqlStr.indexOf(" set ");
        int whereIdx = sqlStr.indexOf(" where ");
        String setClause = sqlStr.substring(setIdx, whereIdx);
        assertFalse(setClause.contains("`user_id` = ?"));
        assertFalse(setClause.contains("`role_id` = ?"));
        assertTrue(setClause.contains("`permission` = ?"));
        // paras: permission值, user_id值, role_id值
        assertEquals(3, paras.size());
        assertEquals("admin", paras.get(0));
        assertEquals(1L, paras.get(1));
        assertEquals(2L, paras.get(2));
    }

    // ==================== 4. Dialect其他SQL生成测试 ====================

    @Test
    public void testMysqlDialectForDbSave() {
        MysqlDialect dialect = new MysqlDialect();
        Record record = new Record();
        record.set("user_name", "张三");
        record.set("age", 25);

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbSave("user_info", new String[]{"id"}, record, sql, paras);

        assertTrue(sql.toString().contains("insert into `user_info`"));
        assertTrue(sql.toString().contains("`user_name`"));
        assertEquals(2, paras.size());
    }

    @Test
    public void testMysqlDialectForDbFindById() {
        MysqlDialect dialect = new MysqlDialect();
        String sql = dialect.forDbFindById("user_info", new String[]{"id"});
        assertEquals("select * from `user_info` where `id` = ?", sql);
    }

    @Test
    public void testMysqlDialectForDbDeleteById() {
        MysqlDialect dialect = new MysqlDialect();
        String sql = dialect.forDbDeleteById("user_info", new String[]{"id"});
        assertEquals("delete from `user_info` where `id` = ?", sql);
    }

    @Test
    public void testMysqlDialectForPaginate() {
        MysqlDialect dialect = new MysqlDialect();
        String sql = dialect.forPaginate(2, 10, new StringBuilder("SELECT * FROM user_info"));
        assertTrue(sql.contains("limit 10, 10"));
    }

    @Test
    public void testOracleDialectForPaginate() {
        OracleDialect dialect = new OracleDialect();
        String sql = dialect.forPaginate(2, 10, new StringBuilder("SELECT * FROM user_info"));
        assertTrue(sql.contains("rownum"));
        assertTrue(sql.contains("20")); // end = page * size
        assertTrue(sql.contains("10")); // start = (page-1) * size
    }

    @Test
    public void testPostgreSqlDialectForPaginate() {
        PostgreSqlDialect dialect = new PostgreSqlDialect();
        String sql = dialect.forPaginate(2, 10, new StringBuilder("SELECT * FROM user_info"));
        assertTrue(sql.contains("limit 10"));
        assertTrue(sql.contains("offset 10"));
    }

    @Test
    public void testSqlite3DialectForPaginate() {
        Sqlite3Dialect dialect = new Sqlite3Dialect();
        String sql = dialect.forPaginate(2, 10, new StringBuilder("SELECT * FROM user_info"));
        assertTrue(sql.contains("limit 10, 10"));
    }

    @Test
    public void testDialectIsPrimaryKey() {
        MysqlDialect dialect = new MysqlDialect();
        assertTrue(dialect.isPrimaryKey("id", new String[]{"id"}));
        assertTrue(dialect.isPrimaryKey("ID", new String[]{"id"})); // 大小写不敏感
        assertFalse(dialect.isPrimaryKey("name", new String[]{"id"}));
    }

    @Test
    public void testDialectIsOracle() {
        assertFalse(new MysqlDialect().isOracle());
        assertTrue(new OracleDialect().isOracle());
        assertFalse(new PostgreSqlDialect().isOracle());
        assertFalse(new Sqlite3Dialect().isOracle());
    }

    @Test
    public void testDialectReplaceOrderBy() {
        MysqlDialect dialect = new MysqlDialect();
        String sql = "FROM user_info ORDER BY id ASC";
        String result = dialect.replaceOrderBy(sql);
        assertFalse(result.contains("ORDER BY"));
    }

    @Test
    public void testDialectForPaginateTotalRow() {
        MysqlDialect dialect = new MysqlDialect();
        String totalRowSql = dialect.forPaginateTotalRow("SELECT *", "FROM user_info ORDER BY id", null);
        assertTrue(totalRowSql.startsWith("select count(*)"));
        assertFalse(totalRowSql.contains("ORDER BY"));
    }

    // ==================== 5. Record类型转换边界测试 ====================

    @Test
    public void testRecordGetStrFromNumber() {
        Record record = new Record().set("val", 123);
        assertEquals("123", record.getStr("val"));
    }

    @Test
    public void testRecordGetStrNull() {
        Record record = new Record();
        assertNull(record.getStr("missing"));
    }

    @Test
    public void testRecordGetIntFromLong() {
        Record record = new Record().set("val", 100L);
        assertEquals(Integer.valueOf(100), record.getInt("val"));
    }

    @Test
    public void testRecordGetIntFromString() {
        Record record = new Record().set("val", "42");
        assertEquals(Integer.valueOf(42), record.getInt("val"));
    }

    @Test
    public void testRecordGetIntNull() {
        Record record = new Record();
        assertNull(record.getInt("missing"));
    }

    @Test
    public void testRecordGetLongFromInt() {
        Record record = new Record().set("val", 100);
        assertEquals(Long.valueOf(100L), record.getLong("val"));
    }

    @Test
    public void testRecordGetLongFromString() {
        Record record = new Record().set("val", "9999999999");
        assertEquals(Long.valueOf(9999999999L), record.getLong("val"));
    }

    @Test
    public void testRecordGetDoubleFromInt() {
        Record record = new Record().set("val", 42);
        assertEquals(Double.valueOf(42.0), record.getDouble("val"));
    }

    @Test
    public void testRecordGetDoubleFromString() {
        Record record = new Record().set("val", "3.14");
        assertEquals(3.14, record.getDouble("val"), 0.001);
    }

    @Test
    public void testRecordGetFloatFromDouble() {
        Record record = new Record().set("val", 3.14);
        assertEquals(3.14f, record.getFloat("val"), 0.01f);
    }

    @Test
    public void testRecordGetFloatFromString() {
        Record record = new Record().set("val", "2.5");
        assertEquals(2.5f, record.getFloat("val"), 0.01f);
    }

    @Test
    public void testRecordGetBooleanNull() {
        Record record = new Record();
        assertNull(record.getBoolean("missing"));
    }

    @Test
    public void testRecordGetBigDecimal() {
        BigDecimal bd = new BigDecimal("123.456");
        Record record = new Record().set("val", bd);
        assertEquals(bd, record.getBigDecimal("val"));
    }

    @Test
    public void testRecordGetBigInteger() {
        BigInteger bi = new BigInteger("123456789012345678901234567890");
        Record record = new Record().set("val", bi);
        assertEquals(bi, record.getBigInteger("val"));
    }

    @Test
    public void testRecordGetNumber() {
        Record record = new Record().set("val", 42);
        assertEquals(42, record.getNumber("val").intValue());
    }

    @Test
    public void testRecordGetBytes() {
        byte[] data = {1, 2, 3, 4, 5};
        Record record = new Record().set("val", data);
        assertArrayEquals(data, record.getBytes("val"));
    }

    @Test
    public void testRecordGetWithDefault() {
        Record record = new Record().set("a", 1);
        assertEquals(1, (int) record.get("a", 99));
        assertEquals(99, (int) record.get("b", 99));
    }

    @Test
    public void testRecordSetColumnsFromRecord() {
        Record r1 = new Record().set("a", 1).set("b", 2);
        Record r2 = new Record().set("c", 3);
        r2.setColumns(r1);
        assertEquals(1, (int) r2.get("a"));
        assertEquals(2, (int) r2.get("b"));
        assertEquals(3, (int) r2.get("c"));
    }

    @Test
    public void testRecordRemoveMultipleColumns() {
        Record record = new Record().set("a", 1).set("b", 2).set("c", 3).set("d", 4);
        record.remove("a", "c");
        assertNull(record.get("a"));
        assertNull(record.get("c"));
        assertEquals(2, record.getColumns().size());
    }

    @Test
    public void testRecordClear() {
        Record record = new Record().set("a", 1).set("b", 2);
        record.clear();
        assertEquals(0, record.getColumns().size());
    }

    @Test
    public void testRecordToModel() {
        Record record = new Record().set("USER_NAME", "测试").set("AGE", 25);

        TestUserModel model = record.toModel(TestUserModel.class);
        assertNotNull(model);
        assertEquals("测试", model.getStr("USER_NAME"));
        assertEquals(25, (int) model.getInt("AGE"));
    }

    // ==================== 6. RecordUtil边界测试 ====================

    @Test
    public void testToUnderlineCaseEdgeCases() {
        assertNull(RecordUtil.toUnderlineCase(null));
        assertEquals("id", RecordUtil.toUnderlineCase("id"));
        assertEquals("user_name", RecordUtil.toUnderlineCase("userName"));
        assertEquals("create_time", RecordUtil.toUnderlineCase("createTime"));
        assertEquals("HTML_parser", RecordUtil.toUnderlineCase("HTMLParser"));
        assertEquals("my_URL", RecordUtil.toUnderlineCase("myURL"));
    }

    @Test
    public void testToCamelCaseEdgeCases() {
        assertNull(RecordUtil.toCamelCase(null));
        assertEquals("id", RecordUtil.toCamelCase("id"));
        assertEquals("userName", RecordUtil.toCamelCase("user_name"));
        assertEquals("createTime", RecordUtil.toCamelCase("create_time"));
        // 无下划线的字段保持原样
        assertEquals("username", RecordUtil.toCamelCase("username"));
    }

    @Test
    public void testMapToRecordNull() {
        Record record = RecordUtil.mapToRecord(null);
        assertNotNull(record);
        assertTrue(record.getColumns().isEmpty());
    }

    @Test
    public void testMapToRecordsNull() {
        List<Record> records = RecordUtil.mapToRecords(null);
        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testMapToRecordsEmpty() {
        List<Record> records = RecordUtil.mapToRecords(new ArrayList<>());
        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testRecordToMapNull() {
        Map<String, Object> map = RecordUtil.recordToMap(null);
        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    @Test
    public void testRecordToMapsNull() {
        List<Map<String, Object>> maps = RecordUtil.recordToMaps(null);
        assertNotNull(maps);
        assertTrue(maps.isEmpty());
    }

    @Test
    public void testRecordToMapsEmpty() {
        List<Map<String, Object>> maps = RecordUtil.recordToMaps(new ArrayList<>());
        assertNotNull(maps);
        assertTrue(maps.isEmpty());
    }

    @Test
    public void testMapToBeanNull() {
        Object result = RecordUtil.mapToBean(null, Object.class);
        assertNull(result);
    }

    @Test
    public void testMapToBeanEmpty() {
        Object result = RecordUtil.mapToBean(new HashMap<>(), Object.class);
        assertNull(result);
    }

    @Test
    public void testMapToBeansNull() {
        List<Object> result = RecordUtil.mapToBeans(null, Object.class);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRecordToBeanNull() {
        Object result = RecordUtil.recordToBean(null, Object.class);
        assertNull(result);
    }

    @Test
    public void testRecordToBeansNull() {
        List<Object> result = RecordUtil.recordToBeans(null, Object.class);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRecordToBeansEmpty() {
        List<Object> result = RecordUtil.recordToBeans(new ArrayList<>(), Object.class);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPageRecordToPageMapNull() {
        Page<Map<String, Object>> result = RecordUtil.pageRecordToPageMap(null);
        assertNull(result);
    }

    @Test
    public void testBeanToRecordsNull() {
        List<Record> result = RecordUtil.beanToRecords(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testBeanToRecordsEmpty() {
        List<Record> result = RecordUtil.beanToRecords(new ArrayList<>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testBeanToMapsNull() {
        List<Map<String, Object>> result = RecordUtil.beanToMaps(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFormatSqlWithParams() {
        String sql = "SELECT * FROM user WHERE name = ? AND age = ?";
        String formatted = RecordUtil.formatSql(sql, new Object[]{"test", 25});
        assertEquals("SELECT * FROM user WHERE name = 'test' AND age = '25'", formatted);
    }

    @Test
    public void testFormatSqlNoParams() {
        String sql = "SELECT * FROM user";
        assertEquals(sql, RecordUtil.formatSql(sql, null));
        assertEquals(sql, RecordUtil.formatSql(sql, new Object[]{}));
    }

    // ==================== 7. RecordUtil反射元数据缓存测试 ====================

    @Test
    public void testFieldMetaCacheConsistency() {
        // 调用两次应返回相同的缓存结果
        List<RecordUtil.FieldMeta> metas1 = RecordUtil.getFieldMetas(TestEntity.class);
        List<RecordUtil.FieldMeta> metas2 = RecordUtil.getFieldMetas(TestEntity.class);
        assertSame(metas1, metas2); // 同一个对象引用
    }

    @Test
    public void testFieldMetaWithAnnotations() {
        List<RecordUtil.FieldMeta> metas = RecordUtil.getFieldMetas(AnnotatedEntity.class);
        Map<String, String> fieldColumnMap = new HashMap<>();
        for (RecordUtil.FieldMeta meta : metas) {
            fieldColumnMap.put(meta.field.getName(), meta.columnName);
        }
        // @Column(name = "email_addr") 应解析为 email_addr
        assertEquals("email_addr", fieldColumnMap.get("email"));
        // @Transient 字段应为 null
        assertNull(fieldColumnMap.get("tempField"));
        // 普通字段应转下划线
        assertEquals("user_name", fieldColumnMap.get("userName"));
    }

    @Test
    public void testGetTableNameJpa() {
        assertEquals("t_entity", RecordUtil.getTableName(AnnotatedEntity.class));
    }

    @Test
    public void testGetTableNameByClassName() {
        assertEquals("test_entity", RecordUtil.getTableName(TestEntity.class));
    }

    @Test
    public void testGetTableNameFromInstance() {
        TestEntity entity = new TestEntity();
        assertEquals("test_entity", RecordUtil.getTableName(entity));
    }

    @Test
    public void testAllFieldsInheritanceChain() {
        List<Field> fields = RecordUtil.allFields(ChildEntity.class);
        Set<String> fieldNames = new HashSet<>();
        for (Field f : fields) {
            fieldNames.add(f.getName());
        }
        // 应包含子类和父类字段
        assertTrue(fieldNames.contains("childField"));
        assertTrue(fieldNames.contains("id"));
        assertTrue(fieldNames.contains("userName"));
    }

    @Test
    public void testBeanToRecordInheritance() {
        ChildEntity child = new ChildEntity();
        child.id = 1L;
        child.userName = "parent_field";
        child.childField = "child_value";

        Record record = RecordUtil.beanToRecord(child);
        assertEquals(Long.valueOf(1L), record.get("id"));
        assertEquals("parent_field", record.get("user_name"));
        assertEquals("child_value", record.get("child_field"));
    }

    // ==================== 8. DbException测试 ====================

    @Test
    public void testDbExceptionWithCause() {
        Exception cause = new RuntimeException("root cause");
        DbException ex = new DbException("test message", cause);
        assertEquals("test message", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    public void testDbExceptionWithSql() {
        Exception cause = new RuntimeException("sql error");
        DbException ex = new DbException(cause, "SELECT * FROM bad_table");
        assertTrue(ex.getMessage().contains("SELECT * FROM bad_table"));
        assertSame(cause, ex.getCause());
    }

    @Test(expected = DbException.class)
    public void testDbExceptionOnInvalidSql() {
        Db.execute("COMPLETELY INVALID SQL 12345");
    }

    @Test(expected = DbException.class)
    public void testDbExceptionOnQueryInvalidSql() {
        Db.queryList("SELECT * FROM non_existent_table_xyz");
    }

    // ==================== 9. 分页边界测试 ====================

    @Test
    public void testPaginateEmptyTable() {
        Page<Record> page = Db.paginate(1, 10, "SELECT *", "FROM user_info");
        assertNotNull(page);
        assertEquals(0, page.getList().size());
        assertEquals(0, page.getTotalRow());
        assertEquals(0, page.getTotalPage());
    }

    @Test(expected = DbException.class)
    public void testPaginateInvalidPageNumber() {
        Db.paginate(0, 10, "SELECT *", "FROM user_info");
    }

    @Test(expected = DbException.class)
    public void testPaginateInvalidPageSize() {
        Db.paginate(1, 0, "SELECT *", "FROM user_info");
    }

    @Test
    public void testPaginatePageExceedsTotal() {
        for (int i = 1; i <= 5; i++) {
            Db.execute("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                    new Object[]{"用户" + i, 20 + i});
        }
        Page<Record> page = Db.paginate(999, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(0, page.getList().size());
        assertEquals(5, page.getTotalRow());
    }

    @Test
    public void testPaginateByFullSqlWithParams() {
        for (int i = 1; i <= 20; i++) {
            Db.execute("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                    new Object[]{"用户" + i, 20 + i});
        }
        Page<Record> page = Db.paginateByFullSql(1, 5,
                "SELECT count(*) FROM user_info WHERE age > ?",
                "SELECT * FROM user_info WHERE age > ? ORDER BY id",
                25);
        assertNotNull(page);
        assertTrue(page.getList().size() <= 5);
        assertTrue(page.getTotalRow() > 0);
    }

    @Test
    public void testPaginateLastPage() {
        for (int i = 1; i <= 23; i++) {
            Db.execute("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                    new Object[]{"用户" + i, 20 + i});
        }
        Page<Record> page = Db.paginate(3, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(3, page.getList().size()); // 23 - 20 = 3 records on last page
        assertEquals(3, page.getPageNumber());
        assertEquals(3, page.getTotalPage());
        assertEquals(23, page.getTotalRow());
    }

    // ==================== 10. 事务边界测试 ====================

    @Test
    public void testTxExceptionCausesRollback() {
        try {
            Db.tx(() -> {
                Db.execute("INSERT INTO user_info(user_name, age) VALUES('tx_test', 20)");
                throw new SQLException("模拟异常");
            });
            fail("Should have thrown exception");
        } catch (RuntimeException e) {
            // 预期
        }
        assertEquals(0, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testDoInTransactionRollbackOnError() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                new Object[]{"事务用户1", 20});
        batchSql.addBatch("INSERT INTO non_existent_table(bad_col) VALUES(?)",
                new Object[]{"will_fail"});

        try {
            Db.doInTransaction(batchSql);
            fail("Should have thrown exception");
        } catch (DbException e) {
            // 预期
        }
        // 由于事务回滚，第一条插入也不应存在
        assertEquals(0, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testDoInTransactionNullBatchSql() {
        int result = Db.doInTransaction(null);
        assertEquals(0, result);
    }

    // ==================== 11. BatchSql测试 ====================

    @Test
    public void testBatchSqlWithTypedList() {
        BatchSql batchSql = new BatchSql();
        List<Object> params = new ArrayList<>();
        params.add("test_user");
        params.add(25);
        batchSql.addBatch("INSERT INTO user_info(user_name, age) VALUES(?, ?)", params);

        assertEquals(1, batchSql.getSqlList().size());
        Object[] objects = (Object[]) batchSql.getSqlList().get(0).get("objects");
        assertEquals("test_user", objects[0]);
        assertEquals(25, objects[1]);
    }

    @Test
    public void testBatchSqlNoArgs() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("DELETE FROM user_info");
        assertEquals(1, batchSql.getSqlList().size());
        Object[] objects = (Object[]) batchSql.getSqlList().get(0).get("objects");
        assertEquals(0, objects.length);
    }

    @Test
    public void testBatchSqlClearBatch() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("SQL1");
        batchSql.addBatch("SQL2");
        assertEquals(2, batchSql.getSqlList().size());

        batchSql.clearBatch();
        assertEquals(0, batchSql.getSqlList().size());
    }

    // ==================== 12. Page对象测试 ====================

    @Test
    public void testPageDefaultConstructor() {
        Page<String> page = new Page<>();
        page.setList(Arrays.asList("a", "b"));
        page.setPageNumber(1);
        page.setPageSize(10);
        page.setTotalPage(1);
        page.setTotalRow(2);

        assertEquals(2, page.getList().size());
        assertEquals(1, page.getPageNumber());
        assertEquals(10, page.getPageSize());
        assertEquals(1, page.getTotalPage());
        assertEquals(2, page.getTotalRow());
    }

    @Test
    public void testPageWithConstructor() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        Page<Integer> page = new Page<>(data, 2, 5, 4, 20);

        assertEquals(5, page.getList().size());
        assertEquals(2, page.getPageNumber());
        assertEquals(5, page.getPageSize());
        assertEquals(4, page.getTotalPage());
        assertEquals(20, page.getTotalRow());
    }

    // ==================== 13. 多数据源并发测试 ====================

    @Test
    public void testConcurrentMultiDatasourceAccess() throws Exception {
        // 创建两个独立数据源
        DruidDataSource ds1 = new DruidDataSource();
        ds1.setUrl("jdbc:h2:mem:multi_ds_1;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds1.setUsername("sa");
        ds1.setPassword("");
        ds1.setDriverClassName("org.h2.Driver");
        Db.init("multi_1", ds1);
        Db.use("multi_1").execute("CREATE TABLE IF NOT EXISTS test_t (id INT PRIMARY KEY, name VARCHAR(100))");

        DruidDataSource ds2 = new DruidDataSource();
        ds2.setUrl("jdbc:h2:mem:multi_ds_2;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds2.setUsername("sa");
        ds2.setPassword("");
        ds2.setDriverClassName("org.h2.Driver");
        Db.init("multi_2", ds2);
        Db.use("multi_2").execute("CREATE TABLE IF NOT EXISTS test_t (id INT PRIMARY KEY, name VARCHAR(100))");

        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    String dsName = (idx % 2 == 0) ? "multi_1" : "multi_2";
                    DbPro db = Db.use(dsName);
                    db.execute("INSERT INTO test_t(id, name) VALUES(?, ?)",
                            new Object[]{idx, "user_" + idx});
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(0, errorCount.get());

        int count1 = Db.use("multi_1").queryForInt("SELECT count(*) FROM test_t");
        int count2 = Db.use("multi_2").queryForInt("SELECT count(*) FROM test_t");
        assertEquals(threadCount, count1 + count2);

        // 清理
        Db.use("multi_1").execute("DROP TABLE IF EXISTS test_t");
        Db.use("multi_2").execute("DROP TABLE IF EXISTS test_t");
    }

    // ==================== 14. findByColumnValueRecords边界测试 ====================

    @Test
    public void testFindByColumnValueRecordsNoMatch() {
        List<Record> records = Db.findByColumnValueRecords("user_info", "user_name", "不存在的用户");
        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByColumnValueRecordsMismatch() {
        Db.findByColumnValueRecords("user_info", "user_name,age", "只有一个值");
    }

    // ==================== 15. getRecordValue大小写不敏感测试 ====================

    @Test
    public void testUpdateWithCaseInsensitivePK() {
        // H2返回大写列名，但主键配置为小写 "id"
        Db.execute("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                new Object[]{"测试用户", 25});

        Record record = Db.findById("user_info", "id", 1);
        assertNotNull(record);
        // H2返回大写 ID
        assertNotNull(record.get("ID"));

        // 更新应该能正常工作（通过大小写不敏感匹配找到PK值）
        record.set("AGE", 30);
        boolean updated = Db.update("user_info", "id", record);
        assertTrue(updated);

        Record reloaded = Db.findById("user_info", "id", 1);
        assertEquals(30, ((Number) reloaded.get("AGE")).intValue());
    }

    // ==================== 16. Model类型getter测试 ====================

    @Test
    public void testModelTypedGetters() {
        TestUserModel model = new TestUserModel();
        model.set("str", "hello");
        model.set("integer", 42);
        model.set("longVal", 100L);
        model.set("doubleVal", 3.14);
        model.set("floatVal", 2.5f);
        model.set("boolVal", true);
        model.set("bigDecimal", new BigDecimal("99.99"));
        model.set("bigInteger", new BigInteger("12345678901234567890"));
        model.set("bytes", new byte[]{1, 2, 3});

        assertEquals("hello", model.getStr("str"));
        assertEquals(Integer.valueOf(42), model.getInt("integer"));
        assertEquals(Long.valueOf(100L), model.getLong("longVal"));
        assertEquals(Double.valueOf(3.14), model.getDouble("doubleVal"));
        assertEquals(Float.valueOf(2.5f), model.getFloat("floatVal"));
        assertTrue(model.getBoolean("boolVal"));
        assertEquals(new BigDecimal("99.99"), model.getBigDecimal("bigDecimal"));
        assertEquals(new BigInteger("12345678901234567890"), model.getBigInteger("bigInteger"));
        assertArrayEquals(new byte[]{1, 2, 3}, model.getBytes("bytes"));
    }

    @Test
    public void testModelGetWithDefault() {
        TestUserModel model = new TestUserModel();
        model.set("a", 1);
        assertEquals(1, (int) model.get("a"));
        assertEquals("default", model.get("missing", "default"));
    }

    // ==================== 17. insert返回主键测试 ====================

    @Test
    public void testInsertReturnsAutoIncrementKey() {
        long key1 = Db.insert("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                new Object[]{"用户1", 20});
        long key2 = Db.insert("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
                new Object[]{"用户2", 21});
        assertTrue(key1 > 0);
        assertTrue(key2 > key1);
    }

    // ==================== 18. findBeanById测试 ====================

    @Test
    public void testFindBeanByIdNotFound() {
        // 使用已存在的表 user_info 查询不存在的ID
        Map<String, Object> result = Db.queryForMap("SELECT * FROM user_info WHERE id = ?", 99999);
        assertNotNull(result);
        assertTrue(result.isEmpty()); // EmptyResultDataAccessException 返回空Map
    }

    // ==================== 19. Db.use异常测试 ====================

    @Test(expected = RuntimeException.class)
    public void testUseEmptyConfigName() {
        Db.use("");
    }

    @Test(expected = RuntimeException.class)
    public void testUseNonExistentConfigName() {
        Db.use("absolutely_non_existent_config_name_xyz");
    }

    // ==================== 测试用Model/实体类 ====================

    @Table(name = "user_info")
    public static class TestUserModel extends Model<TestUserModel> {}

    public static class TestEntity {
        Long id;
        String userName;
        int age;
    }

    @Table(name = "t_entity")
    public static class AnnotatedEntity {
        @Id
        Long id;
        String userName;
        @Column(name = "email_addr")
        String email;
        @Transient
        String tempField;
    }

    public static class ParentEntity {
        Long id;
        String userName;
    }

    public static class ChildEntity extends ParentEntity {
        String childField;
    }
}
