package com.jun.plugin.designpatterns.创建型模式.工厂方法模式.简单工厂模式.多个工厂方法模式;

/**
 * 提供2个方法，分别获取 MailSender 、 SmsSender 的实例
 * 
 * 作者: zhoubang 日期：2015年10月26日 下午4:47:19
 */
public class SendFactory {

    
    public Sender produceMail() {
        return new MailSender();
    }

    
    public Sender produceSms() {
        return new SmsSender();
    }
}
