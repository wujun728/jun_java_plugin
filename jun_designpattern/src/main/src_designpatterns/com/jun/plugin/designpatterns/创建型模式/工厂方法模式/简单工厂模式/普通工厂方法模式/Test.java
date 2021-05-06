package com.jun.plugin.designpatterns.创建型模式.工厂方法模式.简单工厂模式.普通工厂方法模式;

/**
 * 测试
 * 
 * 作者: zhoubang 
 * 日期：2015年10月26日 下午4:50:49
 */
public class Test {
    
    public static void main(String[] args) {
        
        SendFactory factory = new SendFactory();
        
        //Sender sender = factory.produce("sms");//创建发送短信实现类
        
        //Sender sender = factory.produce("voice");//创建发送语音实现类
        
        Sender sender = factory.produce("mail");//创建发送邮件实现类
        
        //调用接口方法
        sender.send();
    }
}
