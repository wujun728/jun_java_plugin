package com.jun.plugin.designpatterns.结构型模式.外观模式;

/**
 * 拥有其他所有部件，统一管理，实现解耦
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午2:10:53
 */
public class Computer {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer() {
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    //启动电脑
    public void startup() {
        System.out.println("正在启动电脑...");
        cpu.startup();
        memory.startup();
        disk.startup();
        System.out.println("电脑启动完毕...");
    }

    //关闭电脑
    public void shutdown() {
        System.out.println("正在关闭电脑...");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        System.out.println("电脑关闭完毕...");
    }
}