package com.designpatterns.state;

import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class StateTest {
    @Test
    void test(){
        CourseVideoStateContext courseVideoStateContext = new CourseVideoStateContext();
        courseVideoStateContext.setCourseVideoState(CourseVideoStateContext.PLAY_STATE);

        courseVideoStateContext.play();
        System.out.println(courseVideoStateContext.getCourseVideoState().getClass().getSimpleName());
        courseVideoStateContext.seek();
        System.out.println(courseVideoStateContext.getCourseVideoState().getClass().getSimpleName());
        courseVideoStateContext.pause();
        System.out.println(courseVideoStateContext.getCourseVideoState().getClass().getSimpleName());
        courseVideoStateContext.seek();
        System.out.println(courseVideoStateContext.getCourseVideoState().getClass().getSimpleName());
        courseVideoStateContext.stop();
        System.out.println(courseVideoStateContext.getCourseVideoState().getClass().getSimpleName());
    }
}
