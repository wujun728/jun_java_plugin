package com.designpatterns.visitor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问者模式
 * @author BaoZhou
 * @date 2019/1/5
 */
public class VisitorTest {
    @Test
    void test(){
        List<Course> list = new ArrayList<>();
        FreeCourse freeCourse = new FreeCourse(0,"免费课程");

        PayCourse payCourse =new PayCourse(30,"付费课程",30000);
        list.add(freeCourse);
        list.add(payCourse);
        Boss boss = new Boss();
        Visitor visitor = new Visitor();
        for (Course course : list) {
            course.accept(visitor);
            course.accept(boss);
        }

    }
}
