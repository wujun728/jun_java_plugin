package cn.springmvc.mybatis.entity.auth;

import cn.springmvc.mybatis.entity.BaseEntity;

/**
 * @author Vincent.wang
 *
 */
public class UserRole implements BaseEntity<String> {

    private static final long serialVersionUID = -56720255622342923L;

    private String id;

    /** 用户ID **/
    private String userId;

    /** 角色ID **/
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
