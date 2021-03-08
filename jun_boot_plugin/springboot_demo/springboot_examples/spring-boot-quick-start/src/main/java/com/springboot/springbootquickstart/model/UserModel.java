package com.springboot.springbootquickstart.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/11
 * @Time: 22:17
 * @email: inwsy@hotmail.com
 * Description:
 */
public class UserModel {
    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
