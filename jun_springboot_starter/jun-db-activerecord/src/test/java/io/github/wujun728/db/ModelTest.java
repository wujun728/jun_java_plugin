package io.github.wujun728.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.db.record.*;
import org.junit.*;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Model 模式单元测试
 * 使用 H2 内存数据库，覆盖：
 * 1. 链式 set/get 操作
 * 2. save / update / delete / deleteById
 * 3. findById / find / findFirst / findAll
 * 4. 分页查询 paginate
 * 5. Record 互转
 * 6. 多数据源 use()
 * 7. @Table / @Id 注解解析
 * 8. 事务支持
 */
public class ModelTest {

    private static DataSource dataSource;

    // ==================== Model 定义 ====================

    @Table(name = "user_info")
    public static class UserModel extends Model<UserModel> {
        public static final UserModel dao = new UserModel().dao();
    }

    /** 无 @Table 注解，表名由类名推导: order_item */
    public static class OrderItem extends Model<OrderItem> {
        public static final OrderItem dao = new OrderItem().dao();
    }

    /** 带 @Id 注解，指定主键字段 */
    @Table(name = "sys_config")
    public static class SysConfig extends Model<SysConfig> {
        @Id
        private String configKey;
        public static final SysConfig dao = new SysConfig().dao();
    }

    // ==================== 初始化 ====================

    @BeforeClass
    public static void setup() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:h2:mem:model_test;DB_CLOSE_DELAY=-1;MODE=MySQL");
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

        Db.execute("CREATE TABLE IF NOT EXISTS order_item (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "product_name VARCHAR(200), " +
                "quantity INT, " +
                "price DECIMAL(10,2)" +
                ")");

        Db.execute("CREATE TABLE IF NOT EXISTS sys_config (" +
                "config_key VARCHAR(100) PRIMARY KEY, " +
                "config_value VARCHAR(500)" +
                ")");
    }

    @Before
    public void beforeEach() {
        Db.execute("DELETE FROM user_info");
        Db.execute("DELETE FROM order_item");
        Db.execute("DELETE FROM sys_config");
        Db.execute("ALTER TABLE user_info ALTER COLUMN id RESTART WITH 1");
        Db.execute("ALTER TABLE order_item ALTER COLUMN id RESTART WITH 1");
    }

    @AfterClass
    public static void teardown() {
        Db.execute("DROP TABLE IF EXISTS user_info");
        Db.execute("DROP TABLE IF EXISTS order_item");
        Db.execute("DROP TABLE IF EXISTS sys_config");
    }

    // ==================== 链式操作测试 ====================

    @Test
    public void testChainSetAndGet() {
        UserModel user = new UserModel()
                .set("user_name", "张三")
                .set("age", 25)
                .set("email", "zhangsan@test.com");

        assertEquals("张三", user.get("user_name"));
        assertEquals(25, (int) user.getInt("age"));
        assertEquals("zhangsan@test.com", user.getStr("email"));
    }

    @Test
    public void testGetWithDefault() {
        UserModel user = new UserModel().set("age", 20);
        assertEquals(20, (int) user.getInt("age"));
        assertEquals("default", user.get("missing", "default"));
    }

    @Test
    public void testRemoveAndClear() {
        UserModel user = new UserModel()
                .set("a", 1).set("b", 2).set("c", 3);

        user.remove("a");
        assertNull(user.get("a"));
        assertEquals(2, user.getAttrs().size());

        user.clear();
        assertEquals(0, user.getAttrs().size());
    }

    @Test
    public void testPutMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("user_name", "批量设置");
        map.put("age", 30);

        UserModel user = new UserModel().put(map);
        assertEquals("批量设置", user.getStr("user_name"));
        assertEquals(30, (int) user.getInt("age"));
    }

    @Test
    public void testGetAttrNamesAndValues() {
        UserModel user = new UserModel().set("a", 1).set("b", 2);
        assertEquals(2, user.getAttrNames().length);
        assertEquals(2, user.getAttrValues().length);
    }

    // ==================== CRUD 测试 ====================

    @Test
    public void testSave() {
        boolean result = new UserModel()
                .set("user_name", "张三")
                .set("age", 25)
                .set("email", "zhangsan@test.com")
                .save();

        assertTrue(result);
        assertEquals(1, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testSaveAndFindById() {
        new UserModel()
                .set("user_name", "李四")
                .set("age", 30)
                .set("email", "lisi@test.com")
                .save();

        UserModel found = UserModel.dao.findById(1);
        assertNotNull(found);
        // H2 返回大写列名
        assertEquals("李四", found.getStr("USER_NAME"));
        assertEquals(30, (int) found.getInt("AGE"));
    }

    @Test
    public void testUpdate() {
        new UserModel()
                .set("user_name", "原名")
                .set("age", 20)
                .save();

        UserModel user = UserModel.dao.findById(1);
        assertNotNull(user);

        boolean updated = user.set("USER_NAME", "新名").update();
        assertTrue(updated);

        UserModel reloaded = UserModel.dao.findById(1);
        assertEquals("新名", reloaded.getStr("USER_NAME"));
    }

    @Test
    public void testDelete() {
        new UserModel()
                .set("user_name", "待删除")
                .set("age", 20)
                .save();

        UserModel user = UserModel.dao.findById(1);
        assertNotNull(user);

        boolean deleted = user.delete();
        assertTrue(deleted);
        assertEquals(0, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testDeleteById() {
        new UserModel().set("user_name", "用户1").set("age", 20).save();
        new UserModel().set("user_name", "用户2").set("age", 21).save();

        boolean deleted = UserModel.dao.deleteById(1);
        assertTrue(deleted);
        assertEquals(1, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testDeleteByIdNotFound() {
        boolean deleted = UserModel.dao.deleteById(999);
        assertFalse(deleted);
    }

    // ==================== 查询测试 ====================

    @Test
    public void testFind() {
        insertUsers(5);
        List<UserModel> users = UserModel.dao.find("SELECT * FROM user_info WHERE AGE > ?", 22);
        assertNotNull(users);
        assertTrue(users.size() > 0);
        // 验证返回的是 UserModel 类型
        for (UserModel u : users) {
            assertNotNull(u.getStr("USER_NAME"));
        }
    }

    @Test
    public void testFindNoArgs() {
        insertUsers(3);
        List<UserModel> users = UserModel.dao.find("SELECT * FROM user_info");
        assertEquals(3, users.size());
    }

    @Test
    public void testFindFirst() {
        insertUsers(3);
        UserModel user = UserModel.dao.findFirst("SELECT * FROM user_info ORDER BY id");
        assertNotNull(user);
        assertEquals("用户1", user.getStr("USER_NAME"));
    }

    @Test
    public void testFindFirstNoResult() {
        UserModel user = UserModel.dao.findFirst("SELECT * FROM user_info WHERE id = ?", 999);
        assertNull(user);
    }

    @Test
    public void testFindAll() {
        insertUsers(4);
        List<UserModel> users = UserModel.dao.findAll();
        assertEquals(4, users.size());
    }

    @Test
    public void testFindByIdNotFound() {
        UserModel user = UserModel.dao.findById(999);
        assertNull(user);
    }

    // ==================== 分页测试 ====================

    @Test
    public void testPaginate() {
        insertUsers(25);
        Page<UserModel> page = UserModel.dao.paginate(1, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(10, page.getList().size());
        assertEquals(1, page.getPageNumber());
        assertEquals(10, page.getPageSize());
        assertEquals(25, page.getTotalRow());
        assertEquals(3, page.getTotalPage());

        // 验证第二页
        Page<UserModel> page2 = UserModel.dao.paginate(2, 10, "SELECT *", "FROM user_info ORDER BY id");
        assertEquals(10, page2.getList().size());
        assertEquals(2, page2.getPageNumber());
    }

    @Test
    public void testPaginateWithParams() {
        insertUsers(20);
        Page<UserModel> page = UserModel.dao.paginate(1, 5, "SELECT *", "FROM user_info WHERE AGE > ?", 22);
        assertNotNull(page);
        assertTrue(page.getList().size() <= 5);
    }

    @Test
    public void testPaginateByFullSql() {
        insertUsers(15);
        Page<UserModel> page = UserModel.dao.paginateByFullSql(1, 5,
                "SELECT count(*) FROM user_info",
                "SELECT * FROM user_info ORDER BY id");
        assertNotNull(page);
        assertEquals(5, page.getList().size());
        assertEquals(15, page.getTotalRow());
    }

    // ==================== Record 互转测试 ====================

    @Test
    public void testToRecord() {
        UserModel user = new UserModel()
                .set("user_name", "张三")
                .set("age", 25);

        Record record = user.toRecord();
        assertNotNull(record);
        assertEquals("张三", record.getStr("user_name"));
        assertEquals(25, (int) record.getInt("age"));
    }

    @Test
    public void testRecordToModel() {
        insertUsers(1);
        Record record = Db.findById("user_info", "id", 1);
        assertNotNull(record);

        UserModel user = record.toModel(UserModel.class);
        assertNotNull(user);
        assertEquals("用户1", user.getStr("USER_NAME"));
    }

    // ==================== @Table / @Id 注解测试 ====================

    @Test
    public void testTableAnnotation() {
        // UserModel 使用 @Table(name="user_info")
        boolean saved = new UserModel()
                .set("user_name", "注解测试")
                .set("age", 30)
                .save();
        assertTrue(saved);
        assertEquals(1, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testClassNameAsTableName() {
        // OrderItem 无 @Table 注解，表名由类名推导: order_item
        boolean saved = new OrderItem()
                .set("product_name", "商品A")
                .set("quantity", 10)
                .set("price", 99.9)
                .save();
        assertTrue(saved);

        OrderItem found = OrderItem.dao.findById(1);
        assertNotNull(found);
        assertEquals("商品A", found.getStr("PRODUCT_NAME"));
    }

    @Test
    public void testIdAnnotation() {
        // SysConfig 使用 @Id 标注 configKey 字段，主键应解析为 config_key
        boolean saved = new SysConfig()
                .set("config_key", "site.name")
                .set("config_value", "测试站点")
                .save();
        assertTrue(saved);

        SysConfig found = SysConfig.dao.findById("site.name");
        assertNotNull(found);
        assertEquals("测试站点", found.getStr("CONFIG_VALUE"));
    }

    @Test
    public void testIdAnnotationUpdate() {
        new SysConfig()
                .set("config_key", "app.version")
                .set("config_value", "1.0")
                .save();

        SysConfig config = SysConfig.dao.findById("app.version");
        config.set("CONFIG_VALUE", "2.0").update();

        SysConfig reloaded = SysConfig.dao.findById("app.version");
        assertEquals("2.0", reloaded.getStr("CONFIG_VALUE"));
    }

    @Test
    public void testIdAnnotationDelete() {
        new SysConfig()
                .set("config_key", "temp.key")
                .set("config_value", "temp")
                .save();

        boolean deleted = SysConfig.dao.deleteById("temp.key");
        assertTrue(deleted);
        assertNull(SysConfig.dao.findById("temp.key"));
    }

    // ==================== 多数据源测试 ====================

    @Test
    public void testUseMultiDatasource() {
        DruidDataSource ds2 = new DruidDataSource();
        ds2.setUrl("jdbc:h2:mem:model_test2;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds2.setUsername("sa");
        ds2.setPassword("");
        ds2.setDriverClassName("org.h2.Driver");
        Db.init("second", ds2);

        Db.use("second").execute("CREATE TABLE IF NOT EXISTS user_info (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "user_name VARCHAR(100), " +
                "age INT, " +
                "email VARCHAR(200)" +
                ")");

        // 在第二个数据源上保存
        new UserModel().use("second")
                .set("user_name", "从库用户")
                .set("age", 33)
                .save();

        // 从第二个数据源查询
        List<UserModel> users = UserModel.dao.use("second")
                .find("SELECT * FROM user_info");
        assertEquals(1, users.size());
        assertEquals("从库用户", users.get(0).getStr("USER_NAME"));

        // 主数据源应为空
        assertEquals(0, UserModel.dao.findAll().size());

        // 清理
        Db.use("second").execute("DROP TABLE IF EXISTS user_info");
    }

    // ==================== 事务测试 ====================

    @Test
    public void testTxCommit() {
        boolean result = UserModel.dao.tx(() -> {
            new UserModel().set("user_name", "事务1").set("age", 20).save();
            new UserModel().set("user_name", "事务2").set("age", 21).save();
            return true;
        });
        assertTrue(result);
        assertEquals(2, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    @Test
    public void testTxRollback() {
        boolean result = UserModel.dao.tx(() -> {
            new UserModel().set("user_name", "事务1").set("age", 20).save();
            return false; // 回滚
        });
        assertFalse(result);
        assertEquals(0, Db.queryForInt("SELECT count(*) FROM user_info"));
    }

    // ==================== equals/hashCode/toString 测试 ====================

    @Test
    public void testToString() {
        UserModel user = new UserModel().set("user_name", "张三").set("age", 25);
        String str = user.toString();
        assertTrue(str.contains("UserModel"));
        assertTrue(str.contains("user_name"));
        assertTrue(str.contains("张三"));
    }

    @Test
    public void testEquals() {
        UserModel u1 = new UserModel().set("id", 1L).set("name", "test");
        UserModel u2 = new UserModel().set("id", 1L).set("name", "test");
        UserModel u3 = new UserModel().set("id", 2L).set("name", "other");

        assertEquals(u1, u2);
        assertNotEquals(u1, u3);
        assertNotEquals(u1, "not a model");
    }

    @Test
    public void testHashCode() {
        UserModel u1 = new UserModel().set("id", 1L);
        UserModel u2 = new UserModel().set("id", 1L);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    // ==================== 辅助方法 ====================

    private void insertUsers(int count) {
        for (int i = 1; i <= count; i++) {
            new UserModel()
                    .set("user_name", "用户" + i)
                    .set("age", 20 + i)
                    .set("email", "user" + i + "@test.com")
                    .save();
        }
    }
}
