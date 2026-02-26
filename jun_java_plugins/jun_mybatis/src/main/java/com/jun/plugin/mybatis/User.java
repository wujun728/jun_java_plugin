package com.jun.plugin.mybatis;

import java.util.Date;

/**
 * MyBatis 实体类：对应数据库user表
 * 字段映射：
 * id → id（自增主键）
 * userName → user_name（数据库列）
 * age → age
 * createTime → create_time（数据库列）
 */
public class User {
    // 对应数据库表字段
    private Integer id;
    private String userName; // 驼峰命名，适配MyBatis下划线转驼峰配置
    private Integer age;
    private Date createTime; // 对应数据库列create_time，存储创建时间

    // 无参构造器（MyBatis反射实例化必需，不可缺少）
    public User() {}

    // 有参构造器（方便测试，快速创建用户对象，不含id（自增））
    public User(String userName, Integer age, Date createTime) {
        this.userName = userName;
        this.age = age;
        this.createTime = createTime;
    }

    // 全量getter/setter方法（MyBatis结果映射、属性赋值必需，不可缺少）
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    // toString()方法（方便控制台打印对象信息，查看操作结果）
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }
}