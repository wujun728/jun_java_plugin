package com.jun.plugin.designpatterns.创建型模式.工厂方法模式.简单工厂模式.多个静态方法模式;

/**
 * 提供2个静态方法
 * 
 * 作者: zhoubang 
 * 日期：2015年10月26日 下午5:05:50
 */
public class SendFactory {

    
    public static Sender produceMail() {
        return new MailSender();
    }

    
    public static Sender produceSms() {
        return new SmsSender();
    }
}
