package com.designpatterns.decorator;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class MasterDecorate extends AbstractDecorate {
    String masterName;

    public MasterDecorate(String masterName, AbstractObject decorate) {
        super(decorate);
        this.masterName = masterName;
    }

    @Override
    public String property() {
        return masterName + "的" + super.property();
    }


    @Override
    public int weight() {
        return super.weight() + 100;
    }
}
