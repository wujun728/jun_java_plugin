package com.jun.plugin.designpatterns.结构型模式.装饰器模式;

/**
 * 装饰类，拥有被装饰类 Source 的实例
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 上午9:16:10
 */
public class Decorator implements Sourceable {

    private Sourceable source;

    public Decorator(Sourceable source) {
        super();
        this.source = source;
    }

    
    /**
     * 在执行被装饰类方法之前和之后，执行一些其他代码逻辑
     * 
     * 作者: zhoubang 
     * 日期：2015年10月28日 上午9:16:47
     * (non-Javadoc)
     * @see com.demo.design_pattern.结构型模式.装饰器模式.Sourceable#method()
     */
    @Override
    public void method() {
        System.out.println("在执行被装饰类的方法之前，执行其他的代码...");
        source.method();
        System.out.println("在执行被装饰类的方法之后，执行其他的代码...");
    }

}
