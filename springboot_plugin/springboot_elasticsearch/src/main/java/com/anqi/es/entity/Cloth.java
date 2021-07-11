package com.anqi.es.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author anqi
 */
public class Cloth {

    @JSONField(ordinal = 0)
    private String id;

    @JSONField(ordinal = 1)
    private String name;

    @JSONField(ordinal = 2)
    private String desc;

    @JSONField(ordinal = 3)
    private Integer num;

    @JSONField(ordinal = 4)
    private double price;

    @JSONField(ordinal = 5, format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Cloth(String id, String name, String desc, Integer num, double price, Date date) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.num = num;
        this.price = price;
        this.date = date;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
