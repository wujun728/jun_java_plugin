package com.jun.plugin.designpatterns.创建型模式.建造者模式;

/**
 * 实现建造接口。建造对象
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午4:36:22
 */
public class ManBuilder implements PersonBuilder {

    Person person;

    public ManBuilder() {
        person = new Man();
    }

    public void buildBody() {
        person.setBody("建造男人的身体");
    }

    public void buildFoot() {
        person.setFoot("建造男人的脚");
    }

    public void buildHead() {
        person.setHead("建造男人的头");
    }

    public Person buildPerson() {
        return person;
    }
}