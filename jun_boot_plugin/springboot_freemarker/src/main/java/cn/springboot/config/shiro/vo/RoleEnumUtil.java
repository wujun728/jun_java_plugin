package cn.springboot.config.shiro.vo;

import cn.springboot.model.auth.Role;

public enum RoleEnumUtil {
    
    超级管理员("超级管理员","admin_role","超级管理员"),
    普通用户("普通用户","common_role","普通用户");

    private RoleEnumUtil(String name, String roleCode, String remark) {
        this.name = name;
        this.roleCode = roleCode;
        this.remark = remark;
    }

    private String name;
    private String roleCode;
    private String remark;
    
    public Role getRole(){
        Role role = new Role();
        role.setName(this.name());
        role.setCode(this.roleCode);
        role.setRemark(this.remark);
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
