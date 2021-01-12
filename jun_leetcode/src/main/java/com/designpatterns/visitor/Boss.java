package com.designpatterns.visitor;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public class Boss implements IVisitor{
    @Override
    public void visit(FreeCourse course) {
        System.out.println(course.getPrice()+course.getName());
    }

    @Override
    public void visit(PayCourse course) {
        System.out.println(course.getPrice()+course.getName()+course.getPv());
    }
}
