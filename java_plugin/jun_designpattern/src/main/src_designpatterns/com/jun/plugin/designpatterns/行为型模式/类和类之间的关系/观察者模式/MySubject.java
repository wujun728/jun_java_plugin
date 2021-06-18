package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.观察者模式;

public class MySubject extends AbstractSubject {

    @Override
    public void operation() {
        System.out.println("开始更新自身的信息....");
        notifyObservers();
    }

}