package cn.qingyi.Dao.Impl;

import cn.qingyi.Dao.UserDao;
import cn.qingyi.Dao.UserDaoSDJ;
import cn.qingyi.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户持久类
 *
 * @author Wujun
 * @Title: UserDaoImpl类
 * @Description: 用户持久类
 * @version: V1.0
 * @date 2017 -03-15 14:54:45
 */
@Repository
public class UserDaoImpl implements UserDao {

    //获取会话工厂
    @Autowired
    private SessionFactory sessionFactory;

    //当前会话变量
    protected Session currentSession;

    @Autowired
    private UserDaoSDJ userDaoSDJ;

    /**
     * 用户登录验证
     *
     * @param user the user
     * @return the boolean
     * @Title: login方法
     * @Description: 用户登录验证
     * @author Wujun
     * @date 2017 -03-15 14:55:16
     */
    @Override
    public boolean login(User user) {
//        currentSession = sessionFactory.getCurrentSession();
//        user = (User)currentSession.createQuery("from User where username=:username and password=:password")
//                .setParameter("username",user.getUsername())
//                .setParameter("password",user.getPassword())
//                .uniqueResult();
        //使用spring-data-jpa方式
        try {
            user = userDaoSDJ.findByUsername(user.getUsername()).get(0);
        }catch (Exception e){
            user = null;
        }

        if (user == null){
            return false;
        }else{
            System.out.println(user.getUsername() + "登录成功!");
            return true;
        }
    }

    @Override
    //用户注册
    public boolean register(User user) {
        try{
            userDaoSDJ.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 获取用户列表信息
     *
     * @return 返回用户列表
     */
    @Override
    public List<User> getUserList() {
        currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from User").list();
    }
}
