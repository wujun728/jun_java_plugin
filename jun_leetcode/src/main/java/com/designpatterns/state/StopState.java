package com.designpatterns.state;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class StopState extends CourseVideoState {
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
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.SEEK_STATE);
    }

    @Override
    public void stop() {
        System.out.println("停止播放");
    }
}
