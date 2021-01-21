package com.zlinks;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * Copyright (C), 2018-2018, BBG
 * FileName: ArrayTest
 * Author: Wujun
 * Date: 2018/7/9 下午2:24
 * Description:
 */
public class ArrayTest {
    public static void main(String[] args) {

        User2 user1 = new User2("1", "123");
        User2 user2 = new User2("2", "234");
        User2[] users = new User2[2];
        users[0] = user1;
        users[1] = user2;

        User2[] users2 = users.clone();
//
//        System.arraycopy(users, 0, users2, 0, 2);

        user1.setName("3");
        System.out.println(Arrays.asList(users));
        System.out.println(Arrays.asList(users2));

    }
}


class User2 implements Cloneable {
    private String name;
    private String password;

    public User2(String name, String password) {
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
    public int hashCode() {
        return this.getName().hashCode() + this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        User2 user = (User2) obj;
        return this.getName().equals(user.getName());
    }


}

