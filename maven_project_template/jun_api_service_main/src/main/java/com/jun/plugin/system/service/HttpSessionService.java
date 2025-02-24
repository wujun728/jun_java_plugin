package com.jun.plugin.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jun.plugin.system.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * session管理器
 *
 * @author aitangbao
 */
@Service
public class HttpSessionService {

    @Autowired
    private RedisService redisDB;

    @Autowired
    private HttpServletRequest request;

    @Value("${spring.redis.key.prefix.userToken}")
    private String USER_TOKEN_PREFIX;

    @Value("${spring.redis.key.expire.userToken}")
    private int EXPIRE ;

    public String createTokenAndUser(User user) {
        //方便根据id找到redis的key， 修改密码/退出登陆 方便使用
        String token = getRandomToken(32) + "#" + user.getId();
        JSONObject sessionInfo = new JSONObject();
        sessionInfo.put("userId", user.getId());
        sessionInfo.put("username", user.getUsername());
        String key = USER_TOKEN_PREFIX + token;
        //设置该用户已登录的token
        redisDB.setAndExpire(key, sessionInfo.toJSONString(), EXPIRE);
        return token;
    }

    /**
     * 根据token获取userid
     *
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token) {
        if (StringUtils.isBlank(token) || !token.contains("#")) {
            return "";
        } else {
            return token.substring(token.indexOf("#") + 1);
        }
    }

    /**
     * 获取参数中的token
     *
     * @return
     */
    public String getTokenFromHeader() {
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public JSONObject getCurrentSession() {
        String token = getTokenFromHeader();
        if (null != token) {
            if (redisDB.exists(USER_TOKEN_PREFIX + token)) {
                String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + token);
                JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
                return sessionInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 使当前用户的token失效
     */
    public void abortUserByToken() {
        String token = getTokenFromHeader();
        redisDB.del(USER_TOKEN_PREFIX + token);
    }

    /**
     * 使所有用户的token失效
     */
    public void abortAllUserByToken() {
        String token = getTokenFromHeader();
        String userId = getUserIdByToken(token);
        redisDB.delKeys(USER_TOKEN_PREFIX+"*#" + userId);
    }

    /**
     * 使用户的token失效
     */
    public void abortUserByUserId(Integer userId) {
        redisDB.delKeys(USER_TOKEN_PREFIX+"*#" + userId);
    }


    /**
     * 生成随机的token
     *
     * @param length
     * @return
     */
    private String getRandomToken(int length) {
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();

        // 根据length生成相应长度的随机字符串
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                randomStr.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                randomStr.append(String.valueOf(random.nextInt(10)));
            }
        }

        return randomStr.toString();
    }


}
