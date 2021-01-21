package com.zlinks;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2018, BBG
 * FileName: ListTest
 * Author: Wujun
 * Date: 2018/7/9 下午2:19
 * Description:
 */
public class ListTest {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        User user = new User("1", "123");
        users.add(user);
        users.add(new User("2", "345"));

        List<User> users2 = new ArrayList<>();
        users2.addAll(users);
        users.get(0).setName("4");

        System.out.println(users);
        System.out.println(users2);
    }
}

class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }
}
