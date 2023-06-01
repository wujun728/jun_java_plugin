package cn.kiiwii.framework.service.impl;

import cn.kiiwii.framework.controller.User;
import cn.kiiwii.framework.dao.IRedisDAO;
import cn.kiiwii.framework.dao.IUserRedisDAO;
import cn.kiiwii.framework.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2016/9/19.
 */
@Service("userService")
public class UserServiceImpl implements IUserService{

    Logger  logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IRedisDAO redisDAO;
    @Override
    public void set() {
        Object o = this.redisDAO.get("name");
        if(o!=null){
            logger.info("获得session-string的值："+o.toString());
        }else{
            this.redisDAO.setValue("name","session-with-redis");
            logger.info("设置了session-string的值");
        }
    }

    @Override
    public void setObject() {
        Object o = this.redisDAO.get("user");
        if(o!=null){
            if(o instanceof User){
                User user = (User)o;
                logger.info("获得session-user的值："+user.toString());
            }else{
                logger.info("获得session-object："+o.toString());
            }
        }else{
            this.redisDAO.setValue("user",new User("zhangsan",18,170.0f));
            logger.info("设置了session-object的值");
        }
    }

    @Override
    public void setList() {
        Object o = this.redisDAO.getList("user:list");
        if(o!=null){
            if (o instanceof List){
                List<User> users = (List<User>)o;
                logger.info("获得session-list的值："+users.toString());
            }else{
                logger.info("获得session-object的值："+o.toString());
            }
        }else{
            List<User> users = new ArrayList<User>();
            users.add(new User("lisi",18,170.0f));
            users.add(new User("wangwu",24,175.0f));
            users.add(new User("zhaoliu",28,180.0f));
            this.redisDAO.setList("user:list",users);
            logger.info("设置了session-list 的值");
        }
    }

    @Override
    public void setHash() {
        Object o = this.redisDAO.getHash("hash");
        if(o!=null){
            if (o instanceof Map){
                Map<String,User> hash = (Map<String,User>)o;
                logger.info("获得session-hash的值："+hash.toString());
            }else{
                logger.info("获得session-object的值："+o.toString());
            }
        }else{
            Map<String,User> hash = new HashMap<>();
            hash.put("1",new User("lisi",18,170.0f));
            hash.put("custom",new User("wangwu",24,175.0f));
            hash.put("admin",new User("zhaoliu",28,180.0f));
            this.redisDAO.setHash("hash",hash);
            logger.info("设置了session-hash 的值");
        }
    }
}
