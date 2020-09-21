package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.entity.Page;

import javax.validation.constraints.NotNull;

import java.util.Date;

public class DemoIO extends Page {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	private String name;
	private Integer age;
	private String pos;
	private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
