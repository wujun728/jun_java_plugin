package com.designpatterns.decorator;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public abstract class AbstractDecorate implements AbstractObject {
    AbstractObject decorate;

    public AbstractDecorate(AbstractObject decorate) {
        this.decorate = decorate;
    }

    @Override
    public String property() {
        return decorate.property();
    }

    @Override
    public int weight() {
        return decorate.weight();
    }
}
