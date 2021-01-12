package com.designpatterns.command;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class CloseCourseCommand implements Command {
    private Course course;

    public CloseCourseCommand(Course course) {
        this.course = course;
    }
    @Override
    public void execute() {
        course.close();
    }
}
