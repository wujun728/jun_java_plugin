package com.designpatterns.command;

import org.junit.jupiter.api.Test;

/**
 * 命令模式
 * @author BaoZhou
 * @date 2019/1/4
 */
public class CommendTest {
    @Test
    void test(){
        Staff staff = new Staff();
        Course course = new Course("数学");
        Course course2 = new Course("语文");
        staff.addCommand(new OpenCourseCommand(course));
        staff.addCommand(new CloseCourseCommand(course));
        staff.addCommand(new OpenCourseCommand(course2));
        staff.addCommand(new CloseCourseCommand(course2));
        staff.addCommand(new OpenCourseCommand(course2));
        staff.exec();
    }
}
