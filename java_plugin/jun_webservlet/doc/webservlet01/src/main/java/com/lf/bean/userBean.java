package com.lf.bean;

import java.util.Date;

/**
 * @ClassName: userBean
 * @Description:
 * @Author: 李峰
 * @Date: 2021 年 01月 20 12:58
 * @Version 1.0
 */
public class userBean {
    private int id;
    private String naem;
    private String  sex;
    private String address;
    private Date date;

    public userBean() {
    }

    public userBean(String naem, String sex, String address, Date date) {
        this.naem = naem;
        this.sex = sex;
        this.address = address;
        this.date = date;
    }

    public userBean(int id, String naem, String sex, String address, Date date) {
        this.id = id;
        this.naem = naem;
        this.sex = sex;
        this.address = address;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaem() {
        return naem;
    }

    public void setNaem(String naem) {
        this.naem = naem;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
