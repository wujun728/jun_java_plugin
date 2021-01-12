package com.designpatterns.visitor;

/**
 * @author BaoZhou
 * @date 2019/1/5
 */
public interface Course {
    void accept(IVisitor visitor);
}
