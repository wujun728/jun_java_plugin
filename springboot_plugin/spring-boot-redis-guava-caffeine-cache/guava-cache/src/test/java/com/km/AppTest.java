package com.km;

import com.km.db.DBUtil;
import com.km.entity.User;
import com.km.service.UserService;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * 测试缓存是否生效
     */
    @Test
    public void test() {
        UserService userService = UserService.INSTANCE;
        User user = userService.get(1L);
        System.out.println(user.getName());
        user.setName("haha");
        System.out.println(user.getName());
        User user1 = userService.get(1L);
        System.out.println(user1.getName());
        // 删除缓存中数据
        userService.remove(1L);
        User user2 = userService.get(1L);
        System.out.println(user2.getName());
    }

    @Test
    public void testDb() throws SQLException {
        User user = null;
        DBUtil dbUtil = new DBUtil();
        ResultSet rs = dbUtil.select("user", "id, username, password", "where id = 1");
        while (rs.next()) {
            long id = rs.getLong("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            user = new User(id, username, password);
        }
        System.out.println(user.toString());
    }

}
