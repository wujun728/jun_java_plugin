package com.zh.springbootshiro.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Wujun
 * @date 2019/6/21
 */
@Data
@ToString
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1748661428525631994L;

    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Set<String> roles;

    private Set<String> perms;

    public User(Integer id, String username, String password, String salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
