package com.zh.springbootshiro.service.impl;

import com.zh.springbootshiro.model.User;
import com.zh.springbootshiro.service.UserService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 * @date 2019/6/25
 */
@Service
public class UserServiceImpl implements UserService {

    private static Map<String, User> userMap = new HashMap<>(16);

    static {
        //明文密码:111111
        userMap.put("张三",new User(1,"张三","9787fe0770108f8027140811f31cb180","abcde"));
        //明文密码:111111
        userMap.put("李四",new User(2,"李四","83cf8ae22c7acc592052498d63d96832","fghjk"));
    }

    @Override
    public User findByUserName(String userName) {
        return userMap.get(userName);
    }

    public static void main(String[] args) {
        String salt = "abcde";
        String hashedPwd = new Md5Hash("111111", salt, 3).toHex();
        System.out.println(hashedPwd);
    }

}
