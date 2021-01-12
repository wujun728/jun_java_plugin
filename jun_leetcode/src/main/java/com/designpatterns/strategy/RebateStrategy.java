package com.designpatterns.strategy;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class RebateStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("返券");
    }
}
