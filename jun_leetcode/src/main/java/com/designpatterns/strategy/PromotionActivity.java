package com.designpatterns.strategy;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class PromotionActivity {


    private PromotionStrategy promotionStrategy;

    public void exec() {
        if (promotionStrategy != null) {
            promotionStrategy.doPromotion();
        }
    }

    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

}
