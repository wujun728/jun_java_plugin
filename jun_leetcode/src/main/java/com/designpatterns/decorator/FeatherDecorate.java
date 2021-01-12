package com.designpatterns.decorator;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class FeatherDecorate extends AbstractDecorate {
    public FeatherDecorate(AbstractObject decorate) {
        super(decorate);
    }

    @Override
    public String property() {
        return "黑毛的"+super.property();
    }


    @Override
    public int weight() {
        return super.weight() + 20;
    }
}
