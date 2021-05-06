package com.jun.plugin.designpatterns.行为型模式.通过中间类.访问者模式;

public class Test {

    public static void main(String[] args) {
        
        //创建需要被访问的对象
        Visitor visitor = new MyVisitor();
        
        //创建接收对象
        Subject sub = new MySubject();
        
        //接受将要访问它的对象
        sub.accept(visitor);
    }
}