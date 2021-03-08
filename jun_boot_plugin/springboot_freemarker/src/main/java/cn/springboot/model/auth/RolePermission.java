package cn.springboot.model.auth;

import cn.springboot.model.BaseEntity;

/** 
 * @Description 角色与菜单关系对象
 * @author Wujun
 * @date Apr 12, 2017 9:11:20 AM  
 */
public class RolePermission implements BaseEntity<String> {

    private static final long serialVersionUID = -7948507636703811294L;

    private String id;

    /** 角色ID **/
    private String roleId;

    /** 菜单ID **/
    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

}
