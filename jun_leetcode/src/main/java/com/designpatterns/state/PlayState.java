package com.designpatterns.state;

/**
 * 状态模式
 * @author BaoZhou
 * @date 2019/1/5
 */
public class PlayState extends CourseVideoState {
    @Override
    public void play() {
        System.out.println("正在播放");
    }

    @Override
    public void pause() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.PAUSE_STATE);
    }

    @Override
    public void seek() {
        System.out.println("ERROR 不能进入缓冲");
    }

    @Override
    public void stop() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.STOP_STATE);
    }
}
