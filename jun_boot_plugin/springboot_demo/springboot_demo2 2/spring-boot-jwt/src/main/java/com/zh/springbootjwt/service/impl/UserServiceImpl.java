package com.zh.springbootjwt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootjwt.model.User;
import com.zh.springbootjwt.service.UserService;
import com.zh.springbootjwt.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Wujun
 * @date 2019/6/25
 */
@Service
public class UserServiceImpl implements UserService {

    private static Map<Integer,User> map = new HashMap<>(16);

    static {
        map.put(1,new User(1,"张三","111111"));
        map.put(2,new User(2,"李四","222222"));
        map.put(3,new User(3,"王五","333333"));
        map.put(4,new User(4,"赵六","444444"));
        map.put(5,new User(5,"田七","555555"));
    }

    @Override
    public User findByUserId(Integer userId) {
        return map.get(userId);
    }

    @Override
    public JSONObject login(String username,String password) {
        JSONObject result = new JSONObject();
        User user =  map.entrySet()
                        .stream()
                        .filter(e -> e.getValue().getUsername().equals(username))
                        .findFirst()
                        .map(e -> e.getValue())
                        .orElse(null);
        if (user != null && password.equals(user.getPassword())){
            result.put("result","SUCCESS");
            result.put("token", JwtUtil.createToken(user));
        }else{
            result.put("result","FAIL");
            result.put("msg", "用户名或密码错误");
        }
        return result;
    }
}
