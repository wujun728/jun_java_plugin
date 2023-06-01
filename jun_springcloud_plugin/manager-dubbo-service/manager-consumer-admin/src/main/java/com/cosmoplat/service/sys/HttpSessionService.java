package com.cosmoplat.service.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.entity.sys.SysUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * session管理器
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class HttpSessionService {
    @Resource
    private RedisService redisService;
    @DubboReference
    private UserRoleProviderService userRoleProviderService;
    @DubboReference
    private RolePermissionProviderService rolePermissionProviderService;
    @Resource
    private HttpServletRequest request;
    @DubboReference
    private PermissionProviderService permissionProviderService;
    @DubboReference
    private RoleProviderService roleProviderService;

    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;

    @Value("${spring.redis.key.expire.userToken}")
    private int exire;

    public String createTokenAndUser(SysUser user, List<String> roles, Set<String> permissions, String uucToken) {
        //方便根据id找到redis的key， 修改密码/退出登陆 方便使用
        String token = UUID.randomUUID().toString() + "#" + user.getId();
        if (StringUtils.isNotBlank(uucToken)) {
            token = uucToken + "#" + user.getId();
        }
        JSONObject sessionInfo = new JSONObject();
        sessionInfo.put(Constant.USERID_KEY, user.getId());
        sessionInfo.put(Constant.USERNAME_KEY, user.getUsername());
        sessionInfo.put(Constant.ROLES_KEY, roles);
        sessionInfo.put(Constant.PERMISSIONS_KEY, permissions);
        String key = userTokenPrefix + token;
        //设置该用户已登录的token
        redisService.setAndExpire(key, sessionInfo.toJSONString(), exire);
        return token;
    }


    /**
     * 根据token获取userid
     *
     * @param token token
     * @return userid
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
     * @return token
     */
    public String getTokenFromHeader() {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constant.ACCESS_TOKEN);
        }
        return token;
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

    /**
     * 获取当前session信息 username
     *
     * @return username
     */
    public String getCurrentUsername() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERNAME_KEY);
        } else {
            return null;
        }
    }

    /**
     * 获取当前session信息 UserId
     *
     * @return UserId
     */
    public String getCurrentUserId() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERID_KEY);
        } else {
            return null;
        }
    }


    /**
     * 使当前用户的token失效
     */
    public void abortUserByToken() {
        String token = getTokenFromHeader();
        redisService.del(userTokenPrefix + token);
    }

    /**
     * 使所有用户的token失效
     */
    public void abortAllUserByToken() {
        String token = getTokenFromHeader();
        String userId = getUserIdByToken(token);
        redisService.delKeys(userTokenPrefix + "*#" + userId);
    }

    /**
     * 使用户的token失效
     */
    public void abortUserById(String userId) {
        redisService.delKeys(userTokenPrefix + "*#" + userId);
    }

    /**
     * 使多个用户的token失效
     */
    public void abortUserByUserIds(List<String> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (String id : userIds) {
                redisService.delKeys(userTokenPrefix + "*#" + id);
            }

        }
    }

    /**
     * 根据用户id， 刷新redis用户权限
     *
     * @param userId userId
     */
    public void refreshUerId(String userId) {

        Set<String> keys = redisService.keys("#" + userId);
        //如果修改了角色/权限， 那么刷新权限
        for (String key : keys) {
            JSONObject redisSession = JSON.parseObject(redisService.get(key));

            List<String> roleNames = getRolesByUserId(userId);
            if (!CollectionUtils.isEmpty(roleNames)) {
                redisSession.put(Constant.ROLES_KEY, roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            redisSession.put(Constant.PERMISSIONS_KEY, permissions);
            Long redisTokenKeyExpire = redisService.getExpire(key);
            //刷新token绑定的角色权限
            redisService.setAndExpire(key, redisSession.toJSONString(), redisTokenKeyExpire);

        }
    }

    /**
     * 根据角色id， 刷新redis用户权限
     *
     * @param roleId roleId
     */
    public void refreshRolePermission(String roleId) {
        List<String> userIds = userRoleProviderService.getUserIdsByRoleId(roleId);
        if (!CollectionUtils.isEmpty(userIds)) {
            userIds.parallelStream().forEach(this::refreshUerId);
        }
    }

    /**
     * 根据权限id， 刷新redis用户权限
     *
     * @param permissionId permissionId
     */
    public void refreshPermission(String permissionId) {
        List<String> userIds = permissionProviderService.getUserIdsById(permissionId);
        if (!CollectionUtils.isEmpty(userIds)) {
            userIds.parallelStream().forEach(this::refreshUerId);
        }
    }


    private List<String> getRolesByUserId(String userId) {
        return roleProviderService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionProviderService.getPermissionsByUserId(userId);
    }

}
