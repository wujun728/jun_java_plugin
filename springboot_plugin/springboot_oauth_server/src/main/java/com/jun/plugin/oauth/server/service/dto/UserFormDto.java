package com.jun.plugin.oauth.server.service.dto;

import com.jun.plugin.oauth.server.domain.user.Privilege;
import com.jun.plugin.oauth.server.domain.user.User;
import com.jun.plugin.oauth.server.infrastructure.PasswordHandler;

/**
 * 2016/3/25
 *
 * @author Wujun
 */
public class UserFormDto extends UserDto {
    private static final long serialVersionUID = 7959857016962260738L;


    private String password;

    public UserFormDto() {
    }


    public Privilege[] getAllPrivileges() {
        return new Privilege[]{Privilege.MOBILE, Privilege.UNITY};
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User newUser() {
        final User user = new User()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(PasswordHandler.encode(getPassword()));
        user.privileges().addAll(getPrivileges());
        return user;
    }
}
