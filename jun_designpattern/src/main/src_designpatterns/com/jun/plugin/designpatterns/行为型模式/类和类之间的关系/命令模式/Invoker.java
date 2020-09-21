package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.命令模式;

/**
 * 模拟司令员
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:24:17
 */
public class Invoker {

    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    //发起命令
    public void action() {
        command.exe();
    }
}