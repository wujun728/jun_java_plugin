package com.jun.plugin.designpatterns.创建型模式.工厂方法模式;

/**
 * 
 * 作者: zhoubang 日期：2015年10月26日 下午4:50:49
 */
public class Test {

    public static void main(String[] args) {

        //生产对象
        //Provider provider = new SendMailFactory();//获取发送邮件工厂实例
        Provider provider = new SendSmsFactory();//获取发送短信工厂实例
        
        //调用生成对象的方法，获取对应的实例
        Sender sender = provider.produce();
        
        //调用实例方法
        sender.send();
    }
    
    
    
    /*
     * 
     * 【工厂方法模式】好处就是，如果你现在想增加一个功能：发及时信息，则只需做一个实现类，实现Sender接口，同时做一个工厂类，实现Provider接口，就OK了，这样做，拓展性较好！无需去改动现成的代码(比如在之前的SendFactory里面新增一个法)。
     * 
     * */
}
