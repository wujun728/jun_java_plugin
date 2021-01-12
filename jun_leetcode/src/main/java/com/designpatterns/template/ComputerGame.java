package com.designpatterns.template;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class ComputerGame extends Game{
    @Override
    void initialize() {
        System.out.println("电脑游戏初始化...");
    }

    @Override
    void startPlay() {
        System.out.println("电脑游戏开始了...");
    }

    @Override
    void endPlay() {
        System.out.println("电脑游戏关闭...");
    }

    @Override
    protected boolean needPlayGame() {
        return true;
    }
}
