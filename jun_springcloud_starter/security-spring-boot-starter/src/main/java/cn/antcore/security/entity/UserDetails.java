package cn.antcore.security.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据详情
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 20210325L;

    /** 用户ID **/
    private Serializable id;

    /** 用户名 **/
    private String username;

    /** 用户数据 **/
    private Serializable target;

    /** 权限集合 **/
    private List<String> authority = new ArrayList<>();

    /** 角色集合 **/
    private List<String> roles = new ArrayList<>();

    public UserDetails(Serializable id, String username) {
        this.id = id;
        this.username = username;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Serializable getTarget() {
        return target;
    }

    public void setTarget(Serializable target) {
        this.target = target;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
