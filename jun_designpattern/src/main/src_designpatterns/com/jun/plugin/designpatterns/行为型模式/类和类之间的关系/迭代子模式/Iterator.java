package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.迭代子模式;

public interface Iterator {
    // 前移
    public Object previous();

    // 后移
    public Object next();

    public boolean hasNext();

    // 取得第一个元素
    public Object first();
}