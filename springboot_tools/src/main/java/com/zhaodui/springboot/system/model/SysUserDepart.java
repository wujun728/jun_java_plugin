package com.zhaodui.springboot.system.model;

import javax.persistence.*;

@Table(name = "sys_user_depart")
public class SysUserDepart {
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 部门id
     */
    @Column(name = "dep_id")
    private String depId;

    /**
     * 获取id
     *
     * @return ID - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取部门id
     *
     * @return dep_id - 部门id
     */
    public String getDepId() {
        return depId;
    }

    /**
     * 设置部门id
     *
     * @param depId 部门id
     */
    public void setDepId(String depId) {
        this.depId = depId;
    }

    public SysUserDepart(String id, String userId, String depId) {
        super();
        this.id = id;
        this.userId = userId;
        this.depId = depId;
    }

    public SysUserDepart(String id, String departId) {
        this.userId = id;
        this.depId = departId;
    }
}