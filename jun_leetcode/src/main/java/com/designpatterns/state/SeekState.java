package com.designpatterns.state;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class SeekState extends CourseVideoState {
    @Override
    public void play() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.PLAY_STATE);
    }

    @Override
    public void pause() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.PAUSE_STATE);
    }

    @Override
    public void seek() {
        System.out.println("正在缓冲");
    }

    @Override
    public void stop() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.STOP_STATE);
    }
}
