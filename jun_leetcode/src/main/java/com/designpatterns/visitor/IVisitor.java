package com.designpatterns.visitor;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public interface IVisitor {
    void visit(FreeCourse course);
    void visit(PayCourse course);
}
