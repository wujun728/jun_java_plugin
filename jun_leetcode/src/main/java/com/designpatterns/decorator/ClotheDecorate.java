package com.designpatterns.decorator;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class ClotheDecorate extends AbstractDecorate {
    public ClotheDecorate(AbstractObject decorate) {
        super(decorate);
    }

    @Override
    public String property() {
        return "穿衣服的"+ super.property();
    }

    @Override
    public int weight() {
        return super.weight() + 50;
    }
}
