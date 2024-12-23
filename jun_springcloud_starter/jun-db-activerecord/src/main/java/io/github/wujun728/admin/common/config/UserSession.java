package io.github.wujun728.admin.common.config;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hyz
 * @date 2021/3/3 10:19
 */
@Data
public class UserSession implements java.io.Serializable{
    private static final long serialVersionUID = -7946270162186590546L;
    private Long userId;
    private String token;
    private String tokenName;
    private String userType;
    private Long enterpriseId;
    private Long deptId;
    private Set<String> buttonCodes;
    private Set<String> urls;
    private Map ext;
    // pc后台
    List<Map<String, Object>> currentUserMenu;
    //移动端
    List<Map<String, Object>> currentUserMenuMobile;

}
