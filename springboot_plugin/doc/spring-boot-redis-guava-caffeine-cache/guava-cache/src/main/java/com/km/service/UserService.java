package com.km.service;

import com.km.cache.CacheService;
import com.km.db.DBUtil;
import com.km.entity.User;

import java.sql.ResultSet;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/25.
 */
public class UserService extends CacheService<Long, User> {

    public static UserService INSTANCE = new UserService();

    @Override
    public User load(Long id) throws Exception {
        System.out.println("query db");
        User user = null;
        DBUtil dbUtil = new DBUtil();
        ResultSet rs = dbUtil.select("user", "id, username, password", "where id = 1");
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            user = new User(id, username, password);
        }
        return user;
    }
}
