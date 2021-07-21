package com.jun.plugin.jpa.security;

import java.io.Serializable;
import java.util.Collection;

import com.jun.plugin.jpa.entity.Role;
import com.jun.plugin.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
public class Principal implements Serializable {
    private static final long serialVersionUID = -6477583820961243636L;

    private User user;
    private Collection<Role> roles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return user.getTrueName();
    }

}
