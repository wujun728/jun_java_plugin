package com.designpatterns.strategy;

import org.junit.jupiter.api.Test;

/**
 * 策略模式
 *
 * @author BaoZhou
 * @date 2019/1/2
 */
public class StrategyTest {
    @Test
    void test() {
        PromotionActivity activity1 = new PromotionActivity(new CashBackStrategy());
        activity1.exec();
        PromotionActivity activity2 = new PromotionActivity(new FullReductionStrategy());
        activity2.exec();
        PromotionActivity activity3 = new PromotionActivity(new RebateStrategy());
        activity3.exec();
    }


    @Test
    void test2() {
        PromotionActivity activity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(PromotionStrategyFactory.Key.CashBack));
        activity.exec();

        PromotionActivity activity2 = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy("123"));
        activity2.exec();

        PromotionActivity activity3 = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(PromotionStrategyFactory.Key.FullReduction));
        activity3.exec();
    }
}
