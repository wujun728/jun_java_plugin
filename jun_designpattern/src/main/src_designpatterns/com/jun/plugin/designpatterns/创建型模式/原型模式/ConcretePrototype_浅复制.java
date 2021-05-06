package com.jun.plugin.designpatterns.创建型模式.原型模式;

//摘自：http://www.cnblogs.com/huzi007/p/3884994.html


/**
 * 实现一个克隆自身的操作
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午3:58:51
 */
public class ConcretePrototype_浅复制 extends Prototype_浅复制 {
    
    public ConcretePrototype_浅复制(String name) {
        setName(name);
    }
}
