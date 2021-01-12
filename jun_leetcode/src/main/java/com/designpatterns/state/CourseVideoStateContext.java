package com.designpatterns.state;


/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class CourseVideoStateContext {


    private CourseVideoState courseVideoState;
    public final static PlayState PLAY_STATE = new PlayState();
    public final static PauseState PAUSE_STATE = new PauseState();
    public final static SeekState SEEK_STATE = new SeekState();
    public final static StopState STOP_STATE = new StopState();

    public void setCourseVideoState(CourseVideoState courseVideoState) {
        this.courseVideoState = courseVideoState;
        this.courseVideoState.setCourseVideoStateContext(this);
    }

    public CourseVideoState getCourseVideoState() {
        return courseVideoState;
    }

    public void play() {
        courseVideoState.play();
    }

    public void seek() {
        courseVideoState.seek();
    }

    public void stop() {
        courseVideoState.stop();
    }

    public void pause() {
        courseVideoState.pause();
    }
}
