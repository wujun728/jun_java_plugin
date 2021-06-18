package com.jun.plugin.designpatterns.结构型模式.适配器模式.接口的适配器模式;

/**
 * 继承抽象类 Wrapper2 ，间接实现 Sourceable 接口中的某个方法
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 上午9:05:15
 */
public class SourceSub2 extends Wrapper2 {
    
    /**
     * 只想实现 Sourceable 接口中的 method2 方法。
     * 
     * 作者: zhoubang 
     * 日期：2015年10月28日 上午9:06:13
     * (non-Javadoc)
     * @see com.demo.design_pattern.结构型模式.适配器模式.接口的适配器模式.Wrapper2#method2()
     */
    public void method2() {
        System.out.println("the sourceable interface's second Sub2!");
    }
}
