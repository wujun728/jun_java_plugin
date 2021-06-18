package cn.qingyi.Service.Impl;

import cn.qingyi.Dao.UserDao;
import cn.qingyi.Entity.User;
import cn.qingyi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务层实现类
 *
 * @author qingyi xuelongqy@foxmail.com
 * @Title: UserServiceImpl类
 * @Description: 用户服务层实现类
 * @version: V1.0
 * @date 2017 -03-01 15:34:51
 */
@Service
public class UserServiceImpl implements UserService {

    //用户持久对象
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录服务
     *
     * @return boolean类型
     * @Title: login方法
     * @Description: 用户登录服务
     * @author qingyi xuelongqy@foxmail.com
     * @date 2017 -03-15 14:33:41
     */
    @Override
    @Transactional()
    public boolean login(User user) {
        boolean loginStatus = userDao.login(user);
        return loginStatus;
    }

    //用户注册
    @Override
    public boolean register(User user) {
        return userDao.register(user);
    }
}
