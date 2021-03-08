package cn.springboot.config.shiro.vo;

import java.io.Serializable;
import java.util.List;

import cn.springboot.model.auth.Role;
import cn.springboot.model.auth.User;

public class Principal implements Serializable {
    private static final long serialVersionUID = -6477583820961243636L;

    private User user;
    private List<Role> roles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return user.getTrueName();
    }

}
