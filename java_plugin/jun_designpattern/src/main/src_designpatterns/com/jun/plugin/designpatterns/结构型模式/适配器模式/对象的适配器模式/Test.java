package com.jun.plugin.designpatterns.结构型模式.适配器模式.对象的适配器模式;

public class Test {

    public static void main(String[] args) {
        Source source = new Source();
        Targetable target = new Wrapper(source);
        
        target.method1();
        target.method2();

        /**
         * 
         * 输出结果与【类的适配器模式】的输出结果一样，只是适配的方法不同而已。
         * 
         */
    }

}
