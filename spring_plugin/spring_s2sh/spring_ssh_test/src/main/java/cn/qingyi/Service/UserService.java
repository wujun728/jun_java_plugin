package cn.qingyi.Service;


import cn.qingyi.Entity.User;

/**
 * 用户服务类
 *
 * @author qingyi xuelongqy@foxmail.com
 * @Title: UserService接口
 * @Description: 用户服务类
 * @version: V1.0
 * @date 2017 -03-01 15:32:18
 */
public interface UserService {
    //用户登录服务
    public boolean login(User user);

    //用户注册
    public boolean register(User user);
}
