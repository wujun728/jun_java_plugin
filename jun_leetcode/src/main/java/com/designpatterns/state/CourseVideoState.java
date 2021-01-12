package com.designpatterns.state;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public abstract class CourseVideoState {
    protected CourseVideoStateContext courseVideoStateContext;

    public void setCourseVideoStateContext(CourseVideoStateContext courseVideoStateContext) {
        this.courseVideoStateContext = courseVideoStateContext;
    }

    public abstract void play();

    public abstract void pause();

    public abstract void seek();

    public abstract void stop();

}
