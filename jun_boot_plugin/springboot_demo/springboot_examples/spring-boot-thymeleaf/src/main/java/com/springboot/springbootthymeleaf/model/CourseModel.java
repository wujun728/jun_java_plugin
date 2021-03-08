package com.springboot.springbootthymeleaf.model;

/**
 * @Author: shiyao.wei
 * @Date: 2019/9/16 16:50
 * @Version: 1.0
 * @Desc:
 */
public class CourseModel {
    private Long id;
    private String name;
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
