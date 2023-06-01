package com.cosmoplat.service.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cosmoplat.entity.sys.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * session管理器
 *
 * @author wenbin
 */
@Service
public class HttpSessionService {

    @Resource
    private HttpServletRequest request;
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;
    @Value("${spring.redis.key.expire.userToken}")
    private String defaultExpire;
    @Resource
    private RedisService redisService;

    /**
     * 登陆的时候返回uuc的token 注册的时候返回自己的
     *
     * @param user
     * @param token
     * @param exire
     * @return
     */
    public String createTokenAndUser(SysUser user, String token, Long exire) {
        if (StringUtils.isBlank(token)) {
            token = UUID.randomUUID().toString();
        }
        if (exire == null) {
            exire = Long.valueOf(defaultExpire);
        }
        //方便根据id找到redis的key， 修改密码/退出登陆 方便使用
        String key = userTokenPrefix + token + "#" + user.getId();
        //设置该用户已登录的token
        redisService.setAndExpire(key, JSONObject.toJSONString(user), exire);
        return token + "#" + user.getId();
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
    public String getCurrentUserId() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString("id");
        }
        return null;
    }

    public String getCurrentUserRealName() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString("realName");
        }
        return null;
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public String getCurrentUserAccount() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString("phone");
        }
        return null;
    }

    /**
     * 获取当前session信息
     *
     * @return session信息
     */
    public JSONObject getCurrentSession() {
        String token = getTokenFromHeader();
        if (null != token) {
            if (redisService.exists(userTokenPrefix + token)) {
                String sessionInfoStr = redisService.get(userTokenPrefix + token);
                return JSON.parseObject(sessionInfoStr);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
