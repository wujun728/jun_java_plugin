package io.github.wujun728.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.db.record.*;
import io.github.wujun728.db.record.mapper.BatchSql;
import io.github.wujun728.db.utils.RecordUtil;
import org.junit.*;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

/**
 * ORM框架完整单元测试
 * 使用H2内存数据库，覆盖所有核心功能：
 * 1. SQL模式 - execute, queryList, queryForMap, queryForInt, queryForString等
 * 2. Record模式 - save, find, findById, update, delete, paginate
 * 3. Bean模式 - saveBean, updateBean, deleteBean, findBeanList, findBeanById, findBeanPages
 * 4. 事务支持 - tx, doInTransaction
 * 5. 工具类 - RecordUtil的各种转换方法
 * 6. 多数据源支持
 */
public class DbActiveRecordTest {

    private static DataSource dataSource;

    @BeforeClass
    public static void setup() {
        // 使用H2内存数据库
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        dataSource = ds;

        // 初始化主数据源
        Db.init(dataSource);

        // 创建测试表
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
        // 清空测试数据
        Db.execute("DELETE FROM user_info");
        Db.execute("DELETE FROM sys_config");
        // 重置自增
        Db.execute("ALTER TABLE user_info ALTER COLUMN id RESTART WITH 1");
    }

    @AfterClass
    public static void teardown() {
        Db.execute("DROP TABLE IF EXISTS user_info");
        Db.execute("DROP TABLE IF EXISTS sys_config");
    }

    // ==================== SQL模式测试 ====================

    @Test
    public void testExecute() {
        int result = Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('张三', 25, 'zhangsan@test.com')");
        assertEquals(1, result);
    }

    @Test
    public void testExecuteWithParams() {
        int result = Db.execute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                new Object[]{"李四", 30, "lisi@test.com"});
        assertEquals(1, result);
    }

    @Test
    public void testUpdate() {
        Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('王五', 28, 'wangwu@test.com')");
        int result = Db.update("UPDATE user_info SET age = ? WHERE user_name = ?", 29, "王五");
        assertEquals(1, result);
    }

    @Test
    public void testInsertReturnKey() {
        long key = Db.insert("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                new Object[]{"赵六", 35, "zhaoliu@test.com"});
        assertTrue(key > 0);
    }

    @Test
    public void testBatchExecute() {
        List<Object[]> batchParams = new ArrayList<>();
        batchParams.add(new Object[]{"用户A", 20, "a@test.com"});
        batchParams.add(new Object[]{"用户B", 21, "b@test.com"});
        batchParams.add(new Object[]{"用户C", 22, "c@test.com"});

        int result = Db.batchExecute(
                "INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                batchParams);
        assertEquals(1, result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(3, count);
    }

    @Test
    public void testQueryList() {
        insertTestUsers(3);
        List<Map<String, Object>> list = Db.queryList("SELECT * FROM user_info");
        assertEquals(3, list.size());
    }

    @Test
    public void testQueryListWithParams() {
        insertTestUsers(5);
        List<Map<String, Object>> list = Db.queryList("SELECT * FROM user_info WHERE age > ?", 22);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testQueryForMap() {
        insertTestUsers(1);
        Map<String, Object> map = Db.queryForMap("SELECT * FROM user_info WHERE id = ?", 1);
        assertNotNull(map);
        assertFalse(map.isEmpty());
    }

    @Test
    public void testQueryForInt() {
        insertTestUsers(3);
        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(3, count);
    }

    @Test
    public void testQueryForLong() {
        insertTestUsers(3);
        long count = Db.queryForLong("SELECT count(*) FROM user_info");
        assertEquals(3L, count);
    }

    @Test
    public void testQueryForString() {
        Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('测试', 25, 'test@test.com')");
        String name = Db.queryForString("SELECT user_name FROM user_info WHERE id = ?", 1);
        assertEquals("测试", name);
    }

    @Test
    public void testQueryFirst() {
        insertTestUsers(3);
        Object result = Db.queryFirst("SELECT count(*) FROM user_info");
        assertNotNull(result);
        assertTrue(((Number) result).intValue() == 3);
    }

    @Test
    public void testCount() {
        insertTestUsers(5);
        int count = Db.count("SELECT count(*) FROM user_info");
        assertEquals(5, count);
    }

    // ==================== Record模式 - CRUD测试 ====================

    @Test
    public void testRecordSave() {
        Record record = new Record();
        record.set("user_name", "张三");
        record.set("age", 25);
        record.set("email", "zhangsan@test.com");

        boolean result = Db.save("user_info", record);
        assertTrue(result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(1, count);
    }

    @Test
    public void testRecordSaveWithPrimaryKey() {
        Record record = new Record();
        record.set("user_name", "李四");
        record.set("age", 30);
        record.set("email", "lisi@test.com");

        boolean result = Db.save("user_info", "id", record);
        assertTrue(result);
    }

    @Test
    public void testRecordFindById() {
        insertTestUsers(1);
        Record record = Db.findById("user_info", "id", 1);
        assertNotNull(record);
        assertEquals("用户1", record.getStr("USER_NAME"));
    }

    @Test
    public void testRecordFindByIds() {
        insertTestUsers(3);
        Record record = Db.findByIds("user_info", "id", 2);
        assertNotNull(record);
    }

    @Test
    public void testRecordFind() {
        insertTestUsers(5);
        List<Record> records = Db.find("SELECT * FROM user_info WHERE age > ?", 22);
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }

    @Test
    public void testRecordFindAll() {
        insertTestUsers(3);
        List<Record> records = Db.findAll("user_info");
        assertEquals(3, records.size());
    }

    @Test
    public void testRecordFindFirst() {
        insertTestUsers(3);
        Record record = Db.findFirst("SELECT * FROM user_info ORDER BY id LIMIT 1");
        assertNotNull(record);
    }

    @Test
    public void testRecordUpdate() {
        insertTestUsers(1);
        Record record = Db.findById("user_info", "id", 1);
        assertNotNull(record);
        // H2 returns uppercase column names, so use uppercase to avoid duplicate column issue
        record.set("AGE", 99);
        boolean result = Db.update("user_info", "id", record);
        assertTrue(result);

        Record updated = Db.findById("user_info", "id", 1);
        assertEquals(99, ((Number) updated.get("AGE")).intValue());
    }

    @Test
    public void testRecordDelete() {
        insertTestUsers(1);
        Record record = Db.findById("user_info", "id", 1);
        assertNotNull(record);

        boolean result = Db.delete("user_info", "id", record);
        assertTrue(result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(0, count);
    }

    @Test
    public void testRecordDeleteById() {
        insertTestUsers(1);
        boolean result = Db.deleteById("user_info", "id", 1);
        assertTrue(result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(0, count);
    }

    @Test
    public void testFindByColumnValueRecords() {
        Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('张三', 25, 'zhangsan@test.com')");
        Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('张三', 30, 'zhangsan2@test.com')");

        List<Record> records = Db.findByColumnValueRecords("user_info", "user_name", "张三");
        assertNotNull(records);
        assertEquals(2, records.size());
    }

    // ==================== Record模式 - 分页测试 ====================

    @Test
    public void testPaginate() {
        insertTestUsers(25);
        Page<Record> page = Db.paginate(1, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(10, page.getList().size());
        assertEquals(1, page.getPageNumber());
        assertEquals(10, page.getPageSize());
        assertEquals(25, page.getTotalRow());
        assertEquals(3, page.getTotalPage());
    }

    @Test
    public void testPaginateWithParams() {
        insertTestUsers(20);
        Page<Record> page = Db.paginate(1, 5, "SELECT *", "FROM user_info WHERE age > ?", 22);
        assertNotNull(page);
        assertTrue(page.getList().size() <= 5);
    }

    @Test
    public void testPaginateSecondPage() {
        insertTestUsers(25);
        Page<Record> page = Db.paginate(2, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(10, page.getList().size());
        assertEquals(2, page.getPageNumber());
    }

    @Test
    public void testPaginateByFullSql() {
        insertTestUsers(15);
        Page<Record> page = Db.paginateByFullSql(1, 5,
                "SELECT count(*) FROM user_info",
                "SELECT * FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(5, page.getList().size());
        assertEquals(15, page.getTotalRow());
    }

    @Test
    public void testQueryMapPages() {
        insertTestUsers(20);
        Page<Map<String, Object>> page = Db.queryMapPages(
                "SELECT * FROM user_info ORDER BY id", 1, 10, null);
        assertNotNull(page);
        assertEquals(10, page.getList().size());
        assertEquals(20, page.getTotalRow());
    }

    // ==================== Bean模式测试 ====================

    @Test
    public void testSaveBean() {
        UserInfo user = new UserInfo();
        user.setId(100L);
        user.setUserName("Bean用户");
        user.setAge(28);
        user.setEmail("beanuser@test.com");

        boolean result = Db.saveBean(user);
        assertTrue(result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(1, count);
    }

    @Test
    public void testUpdateBean() {
        UserInfo user = new UserInfo();
        user.setId(100L);
        user.setUserName("Bean用户");
        user.setAge(28);
        user.setEmail("beanuser@test.com");
        Db.saveBean(user);

        user.setAge(35);
        user.setEmail("updated@test.com");
        boolean result = Db.updateBean(user);
        assertTrue(result);

        Map<String, Object> map = Db.queryForMap("SELECT * FROM user_info WHERE id = ?", 100);
        assertEquals(35, ((Number) map.get("AGE")).intValue());
    }

    @Test
    public void testDeleteBean() {
        UserInfo user = new UserInfo();
        user.setId(100L);
        user.setUserName("Bean用户");
        user.setAge(28);
        user.setEmail("beanuser@test.com");
        Db.saveBean(user);

        boolean result = Db.deleteBean(user);
        assertTrue(result);

        int count = Db.queryForInt("SELECT count(*) FROM user_info");
        assertEquals(0, count);
    }

    @Test
    public void testFindBeanList() {
        insertTestUsers(5);
        List<UserInfo> list = Db.findBeanList(UserInfo.class, "SELECT * FROM user_info");
        assertNotNull(list);
        assertEquals(5, list.size());
    }

    @Test
    public void testFindBeanListWithParams() {
        insertTestUsers(10);
        List<UserInfo> list = Db.findBeanList(UserInfo.class,
                "SELECT * FROM user_info WHERE age > ?", 23);
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testFindBeanPages() {
        insertTestUsers(20);
        Page<UserInfo> page = Db.findBeanPages(UserInfo.class, 1, 5,
                "SELECT * FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(5, page.getList().size());
        assertEquals(20, page.getTotalRow());
        assertEquals(4, page.getTotalPage());
    }

    // ==================== 事务测试 ====================

    @Test
    public void testTxCommit() {
        boolean result = Db.tx(() -> {
            Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('事务用户1', 20, 'tx1@test.com')");
            Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('事务用户2', 21, 'tx2@test.com')");
            return true;
        });
        assertTrue(result);
        assertEquals(2, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testTxRollback() {
        boolean result = Db.tx(() -> {
            Db.execute("INSERT INTO user_info(user_name, age, email) VALUES('事务用户1', 20, 'tx1@test.com')");
            return false; // 返回false触发回滚
        });
        assertFalse(result);
        assertEquals(0, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testDoInTransactionWithBatchSql() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                new Object[]{"批量1", 20, "batch1@test.com"});
        batchSql.addBatch("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                new Object[]{"批量2", 21, "batch2@test.com"});
        batchSql.addBatch("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                new Object[]{"批量3", 22, "batch3@test.com"});

        int result = Db.doInTransaction(batchSql);
        assertEquals(1, result);
        assertEquals(3, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    // ==================== Record对象操作测试 ====================

    @Test
    public void testRecordSetAndGet() {
        Record record = new Record();
        record.set("name", "张三");
        record.set("age", 25);
        record.set("score", 98.5);
        record.set("active", true);

        assertEquals("张三", record.getStr("name"));
        assertEquals(Integer.valueOf(25), record.getInt("age"));
        assertEquals(Double.valueOf(98.5), record.getDouble("score"));
        assertTrue(record.getBoolean("active"));
    }

    @Test
    public void testRecordFluentApi() {
        Record record = new Record()
                .set("key1", "value1")
                .set("key2", "value2")
                .set("key3", "value3");

        assertEquals(3, record.getColumns().size());
        assertEquals("value1", record.get("key1"));
    }

    @Test
    public void testRecordGetColumnNames() {
        Record record = new Record();
        record.set("col1", "a");
        record.set("col2", "b");

        String[] names = record.getColumnNames();
        assertEquals(2, names.length);
    }

    @Test
    public void testRecordGetColumnValues() {
        Record record = new Record();
        record.set("col1", "a");
        record.set("col2", "b");

        Object[] values = record.getColumnValues();
        assertEquals(2, values.length);
    }

    @Test
    public void testRecordRemove() {
        Record record = new Record();
        record.set("a", 1);
        record.set("b", 2);
        record.remove("a");

        assertNull(record.get("a"));
        assertEquals(1, record.getColumns().size());
    }

    @Test
    public void testRecordRemoveNullValueColumns() {
        Record record = new Record();
        record.set("a", 1);
        record.set("b", null);
        record.set("c", "hello");
        record.removeNullValueColumns();

        assertEquals(2, record.getColumns().size());
        assertNull(record.get("b"));
    }

    // ==================== RecordUtil工具类测试 ====================

    @Test
    public void testToUnderlineCase() {
        assertEquals("user_name", RecordUtil.toUnderlineCase("userName"));
        assertEquals("create_time", RecordUtil.toUnderlineCase("createTime"));
        assertEquals("id", RecordUtil.toUnderlineCase("id"));
        assertNull(RecordUtil.toUnderlineCase(null));
    }

    @Test
    public void testToCamelCase() {
        assertEquals("userName", RecordUtil.toCamelCase("user_name"));
        assertEquals("createTime", RecordUtil.toCamelCase("create_time"));
        assertEquals("id", RecordUtil.toCamelCase("id"));
        assertNull(RecordUtil.toCamelCase(null));
    }

    @Test
    public void testMapToRecord() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("name", "test");

        Record record = RecordUtil.mapToRecord(map);
        assertNotNull(record);
        assertEquals(Long.valueOf(1L), record.get("id"));
        assertEquals("test", record.get("name"));
    }

    @Test
    public void testMapToRecords() {
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1L);
        maps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2L);
        maps.add(map2);

        List<Record> records = RecordUtil.mapToRecords(maps);
        assertEquals(2, records.size());
    }

    @Test
    public void testRecordToMap() {
        Record record = new Record();
        record.set("id", 1L);
        record.set("name", "test");

        Map<String, Object> map = RecordUtil.recordToMap(record);
        assertNotNull(map);
        assertEquals(1L, map.get("id"));
    }

    @Test
    public void testRecordToMaps() {
        List<Record> records = new ArrayList<>();
        records.add(new Record().set("id", 1L));
        records.add(new Record().set("id", 2L));

        List<Map<String, Object>> maps = RecordUtil.recordToMaps(records);
        assertEquals(2, maps.size());
    }

    @Test
    public void testBeanToRecord() {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setUserName("张三");
        user.setAge(25);

        Record record = RecordUtil.beanToRecord(user);
        assertNotNull(record);
        assertEquals(Long.valueOf(1L), record.get("id"));
        assertEquals("张三", record.get("user_name"));
        assertEquals(Integer.valueOf(25), record.get("age"));
    }

    @Test
    public void testRecordToBean() {
        Record record = new Record();
        record.set("ID", 1L);
        record.set("USER_NAME", "张三");
        record.set("AGE", 25);

        UserInfo user = RecordUtil.recordToBean(record, UserInfo.class);
        assertNotNull(user);
    }

    @Test
    public void testMapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("userName", "张三");
        map.put("age", 25);

        UserInfo user = RecordUtil.mapToBean(map, UserInfo.class);
        assertNotNull(user);
        assertEquals("张三", user.getUserName());
        assertEquals(25, user.getAge());
    }

    @Test
    public void testMapToBeans() {
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1L);
        map1.put("userName", "用户1");
        maps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2L);
        map2.put("userName", "用户2");
        maps.add(map2);

        List<UserInfo> users = RecordUtil.mapToBeans(maps, UserInfo.class);
        assertEquals(2, users.size());
    }

    @Test
    public void testBeanToMap() {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setUserName("张三");
        user.setAge(25);

        Map<String, Object> map = RecordUtil.beanToMap(user);
        assertNotNull(map);
        assertTrue(map.containsValue("张三"));
    }

    @Test
    public void testBeanToRecords() {
        List<UserInfo> users = new ArrayList<>();
        UserInfo u1 = new UserInfo();
        u1.setId(1L);
        u1.setUserName("用户1");
        users.add(u1);
        UserInfo u2 = new UserInfo();
        u2.setId(2L);
        u2.setUserName("用户2");
        users.add(u2);

        List<Record> records = RecordUtil.beanToRecords(users);
        assertEquals(2, records.size());
    }

    @Test
    public void testPageRecordToPageMap() {
        List<Record> list = new ArrayList<>();
        list.add(new Record().set("id", 1L));
        list.add(new Record().set("id", 2L));
        Page<Record> recordPage = new Page<>(list, 1, 10, 1, 2);

        Page<Map<String, Object>> mapPage = RecordUtil.pageRecordToPageMap(recordPage);
        assertNotNull(mapPage);
        assertEquals(2, mapPage.getList().size());
        assertEquals(1, mapPage.getPageNumber());
    }

    @Test
    public void testGetTableName() {
        String tableName = RecordUtil.getTableName(UserInfo.class);
        assertEquals("user_info", tableName);
    }

    @Test
    public void testGetTableNameWithAnnotation() {
        String tableName = RecordUtil.getTableName(AnnotatedUser.class);
        assertEquals("t_user", tableName);
    }

    @Test
    public void testGetColumnName() throws NoSuchFieldException {
        Field field = AnnotatedUser.class.getDeclaredField("userName");
        String columnName = RecordUtil.getColumnName(field);
        assertEquals("user_name", columnName);

        Field emailField = AnnotatedUser.class.getDeclaredField("email");
        String emailColumnName = RecordUtil.getColumnName(emailField);
        assertEquals("email_address", emailColumnName);
    }

    @Test
    public void testFormatSql() {
        String sql = "SELECT * FROM user WHERE name = ? AND age = ?";
        String formatted = RecordUtil.formatSql(sql, new Object[]{"张三", 25});
        assertEquals("SELECT * FROM user WHERE name = '张三' AND age = '25'", formatted);
    }

    @Test
    public void testFormatSqlNoParams() {
        String sql = "SELECT * FROM user";
        String formatted = RecordUtil.formatSql(sql, null);
        assertEquals(sql, formatted);
    }

    @Test
    public void testAllFields() {
        List<Field> fields = RecordUtil.allFields(UserInfo.class);
        assertTrue(fields.size() >= 4); // id, userName, age, email at minimum
    }

    // ==================== 多数据源测试 ====================

    @Test
    public void testMultiDatasource() {
        // 初始化第二个数据源
        DruidDataSource ds2 = new DruidDataSource();
        ds2.setUrl("jdbc:h2:mem:testdb2;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds2.setUsername("sa");
        ds2.setPassword("");
        ds2.setDriverClassName("org.h2.Driver");

        Db.init("second", ds2);

        // 在第二个数据源上操作
        DbPro secondDb = Db.use("second");
        assertNotNull(secondDb);
        secondDb.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(100))");
        secondDb.execute("INSERT INTO test_table(id, name) VALUES(1, 'test')");

        List<Map<String, Object>> result = secondDb.queryList("SELECT * FROM test_table");
        assertEquals(1, result.size());

        // 主数据源不受影响
        DbPro mainDb = Db.use();
        assertNotNull(mainDb);

        // 清理
        secondDb.execute("DROP TABLE IF EXISTS test_table");
    }

    @Test
    public void testUseMainDatasource() {
        DbPro mainDb = Db.use();
        assertNotNull(mainDb);
        assertNotNull(mainDb.getJdbcTemplate());
        assertNotNull(mainDb.getDataSource());
    }

    @Test(expected = RuntimeException.class)
    public void testUseNonExistentDatasource() {
        Db.use("nonexistent");
    }

    // ==================== Dialect测试 ====================

    @Test
    public void testDialectAutoDetection() {
        DbPro dbPro = Db.use();
        assertNotNull(dbPro.getDialectInstance());
        // H2 should map to MysqlDialect
        assertTrue(dbPro.getDialectInstance() instanceof io.github.wujun728.db.record.dialect.MysqlDialect);
    }

    // ==================== Page对象测试 ====================

    @Test
    public void testPageObject() {
        List<String> list = Arrays.asList("a", "b", "c");
        Page<String> page = new Page<>(list, 1, 10, 1, 3);

        assertEquals(3, page.getList().size());
        assertEquals(1, page.getPageNumber());
        assertEquals(10, page.getPageSize());
        assertEquals(1, page.getTotalPage());
        assertEquals(3, page.getTotalRow());
    }

    // ==================== BatchSql测试 ====================

    @Test
    public void testBatchSql() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("INSERT INTO user_info(user_name) VALUES(?)", new Object[]{"a"});
        batchSql.addBatch("INSERT INTO user_info(user_name) VALUES(?)", new Object[]{"b"});

        assertEquals(2, batchSql.getSqlList().size());

        batchSql.clearBatch();
        assertEquals(0, batchSql.getSqlList().size());
    }

    @Test
    public void testBatchSqlWithList() {
        BatchSql batchSql = new BatchSql();
        List<Object> params = new ArrayList<>();
        params.add("test");
        batchSql.addBatch("INSERT INTO user_info(user_name) VALUES(?)", params);

        assertEquals(1, batchSql.getSqlList().size());
    }

    @Test
    public void testBatchSqlNoParams() {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch("DELETE FROM user_info");

        assertEquals(1, batchSql.getSqlList().size());
    }

    // ==================== DbException测试 ====================

    @Test(expected = DbException.class)
    public void testDbExceptionOnInvalidSql() {
        Db.execute("INVALID SQL STATEMENT");
    }

    // ==================== 非标主键表（sys_config）测试 ====================

    @Test
    public void testNonAutoIncrementPrimaryKey() {
        Record config = new Record();
        config.set("config_key", "site.name");
        config.set("config_value", "测试站点");
        boolean saved = Db.save("sys_config", "config_key", config);
        assertTrue(saved);

        Record found = Db.findById("sys_config", "config_key", "site.name");
        assertNotNull(found);
        assertEquals("测试站点", found.getStr("CONFIG_VALUE"));
    }

    @Test
    public void testUpdateNonAutoIncrementPK() {
        Record config = new Record();
        config.set("config_key", "app.version");
        config.set("config_value", "1.0");
        Db.save("sys_config", "config_key", config);

        config.set("config_value", "2.0");
        boolean updated = Db.update("sys_config", "config_key", config);
        assertTrue(updated);

        Record found = Db.findById("sys_config", "config_key", "app.version");
        assertEquals("2.0", found.getStr("CONFIG_VALUE"));
    }

    // ==================== 辅助方法 ====================

    private void insertTestUsers(int count) {
        for (int i = 1; i <= count; i++) {
            Db.execute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)",
                    new Object[]{"用户" + i, 20 + i, "user" + i + "@test.com"});
        }
    }

    // ==================== 测试用实体类 ====================

    /**
     * 普通实体（类名转下划线作为表名）
     */
    public static class UserInfo {
        private Long id;
        private String userName;
        private int age;
        private String email;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    /**
     * 带JPA注解的实体（@Table指定表名, @Column指定列名）
     */
    @javax.persistence.Table(name = "t_user")
    public static class AnnotatedUser {
        @javax.persistence.Id
        private Long id;
        private String userName;
        @javax.persistence.Column(name = "email_address")
        private String email;
        @javax.persistence.Transient
        private String tempField; // 应被跳过

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getTempField() { return tempField; }
        public void setTempField(String tempField) { this.tempField = tempField; }
    }
}
