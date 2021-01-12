package com.designpatterns.visitor;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class Visitor implements IVisitor {
    @Override
    public void visit(FreeCourse course) {
        System.out.println(course.getPrice());
    }

    @Override
    public void visit(PayCourse course) {
        System.out.println(course.getPrice());
    }
}
