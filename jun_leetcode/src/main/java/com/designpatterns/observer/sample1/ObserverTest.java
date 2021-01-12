package com.designpatterns.observer.sample1;

import org.junit.jupiter.api.Test;

/**
 * 观察者模式（JAVA自带观察者模式支持类）
 * @author BaoZhou
 * @date 2019/1/2
 */
public class ObserverTest {
    @Test
    void test(){
        Course course = new Course("课程");
        Teacher bigTeacher = new Teacher("大");
        Teacher smallTeacher = new Teacher("小");
        course.addObserver(bigTeacher);
        course.addObserver(smallTeacher);
        Question question = new Question();
        question.setContent("这个汉堡怎么吃");
        question.setUserName("BaoZhou");
        course.produceQuestion(course,question);
    }
}
