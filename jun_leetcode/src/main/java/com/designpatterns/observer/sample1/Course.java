package com.designpatterns.observer.sample1;

import java.util.Observable;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class Course extends Observable {
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public void produceQuestion(Course course, Question question) {
        System.out.println(question.getUserName() + "在" + course.courseName + "提出了一个问题");
        setChanged();
        notifyObservers(question);
    }


}
