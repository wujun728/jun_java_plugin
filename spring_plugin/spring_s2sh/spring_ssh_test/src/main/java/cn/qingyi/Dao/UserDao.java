package cn.qingyi.Dao;

import cn.qingyi.Entity.User;

import java.util.List;

/**
 * 用户持久接口
 *
 * @author qingyi xuelongqy@foxmail.com
 * @Title: UserDao类
 * @Description: 用户持久接口
 * @version: V1.0
 * @date 2017 -03-15 14:48:36
 */
public interface UserDao {
    //用户登录验证
    public boolean login(User user);

    //用户注册
    public boolean register(User user);

    //获取用户列表
    public List<User> getUserList();
}
