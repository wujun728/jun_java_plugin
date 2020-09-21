package com.jun.plugin.designpatterns.行为型模式.通过中间类.访问者模式;

public class MySubject implements Subject {

    //接受将要访问它的对象
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    
    //获取将要被访问的属性
    @Override
    public String getSubject() {
        return "love";
    }
}