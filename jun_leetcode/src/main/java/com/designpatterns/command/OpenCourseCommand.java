package com.designpatterns.command;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class OpenCourseCommand implements Command {
    private Course course;

    public OpenCourseCommand(Course course) {
        this.course = course;
    }

    @Override
    public void execute() {
        course.open();
    }
}
