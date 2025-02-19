package io.github.wujun728.model;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ModelTest {
    public static void main(String[] args) throws IOException {

        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource dataSource = DataSourcePool.init("main", url, username, password);
        Db.init(dataSource);

        //Register.registerRecord("db_qixing_bk",dataSource); //数据库名


        modelTest();
        jdbcTest();
        recordTest();
    }

    public static void modelTest() {
//        Register.registerModel("io.github.wujun728.model"); //扫描的包名
//        Register.registerClass(User.class);

        //保存User对象
        new User().setMobile("abc")
                .setPassword("123")
                .save();

        //根据主键查询User
        User user = new User().findById(1);

        //获取关联对象
        //异步保存到数据
        //user.asyncSave();
        user.setMobile(user.getMobile()+"111");
        //异步更新到数据
        user.asyncUpdate();
        user.setId(RandomUtil.randomLong());
        user.asyncSave();
    }
    public static void jdbcTest() {
        JdbcTemplate jdbcTemplate = Db.use("main").getDbTemplate().getJdbcTemplate();
        //直接使用JdbcTemplate增加自定义查询 并转换成Model
        Map<String, Object> resultMap = jdbcTemplate.queryForMap("SELECT * FROM c_user WHERE id_ = ?", 1);
        User user = new User().mapping(resultMap);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList("SELECT * FROM c_user");
        List<User> users = new User().mappingList(resultList);
        StaticLog.info(JSONUtil.toJsonPrettyStr(users));

    }

    public static void recordTest() {
//        JdbcTemplate jdbcTemplate = Register.getWriteTemplate();
//        Db.setJdbcTemplate(jdbcTemplate);
        //查询record
        Record record = Db.findById("c_user","id_", 1);


        //转换到Model(转换到Model后直接使用getter就能获取关联Model)
        User user = (User) record.toModel(User.class);

        //保存Record对象
        Record record2 = new Record()
                .set("mobile_", "abc777")
                .set("password_", "123");
        Db.save("c_user", record2);
    }
}
