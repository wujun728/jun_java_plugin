package com.jun.plugin.designpatterns.行为型模式.通过中间类.中介者模式;

/**
 * 中介
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午10:03:29
 */
public interface Mediator {
    
    //管理user1、user2
    public void createMediator();

    //工作
    public void workAll();
}