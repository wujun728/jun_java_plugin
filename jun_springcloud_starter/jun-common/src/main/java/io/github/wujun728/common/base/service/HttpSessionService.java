package io.github.wujun728.common.base.service;

import com.alibaba.fastjson2.JSONObject;
import io.github.wujun728.common.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public interface HttpSessionService {
    /**
     * 根据token获取userid
     *
     * @param token token
     * @return userid
     */
    static String getUserIdByToken(String token) {
        if (StringUtils.isBlank(token) || !token.contains("#")) {
            return "";
        } else {
            return token.substring(token.indexOf("#") + 1);
        }
    }

    String createTokenAndUser(BaseEntity user, List<String> roles, Set<String> permissions, String jwtToken);

    String getTokenFromHeader();

    JSONObject getCurrentSession();

    String getCurrentUsername();

    String getCurrentUserRealname();

    //    @Cacheable(cacheNames = "cache1m")
    String getCurrentUserId();

    void abortUserByToken();

    void abortAllUserByToken();

    void abortUserById(String userId);

    void abortUserByUserIds(List<String> userIds);

    void refreshUerId(String userId);

    void refreshRolePermission(String roleId);

    void refreshPermission(String permissionId);
}
