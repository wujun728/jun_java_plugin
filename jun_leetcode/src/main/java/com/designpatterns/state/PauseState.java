package com.designpatterns.state;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class PauseState extends CourseVideoState {
    @Override
    public void play() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.PLAY_STATE);
    }

    @Override
    public void pause() {
        System.out.println("暂停播放");
    }

    @Override
    public void seek() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.SEEK_STATE);
    }

    @Override
    public void stop() {
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.STOP_STATE);
    }
}
