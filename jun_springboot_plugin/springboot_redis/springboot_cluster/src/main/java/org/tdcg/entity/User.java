package org.tdcg.entity;

import java.io.Serializable;

/**
 * @Title: User
 * @Package: org.tdcg.entity
 * @Description: 测试类，用户实体
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/24
 * @Version: V1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3802423545972880637L;
    private String id;
    private String name;
    private int age;
    private String email;

    public User() {
    }

    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
