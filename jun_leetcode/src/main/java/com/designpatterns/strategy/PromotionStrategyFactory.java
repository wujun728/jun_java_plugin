package com.designpatterns.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class PromotionStrategyFactory {
    private static final PromotionStrategy NON_STRATEGY = new EmptyStrategy();
    private static Map<String, PromotionStrategy> strategyMaps = new HashMap<>(3);

    static {
        strategyMaps.put(Key.CashBack, new CashBackStrategy());
        strategyMaps.put(Key.FullReduction, new FullReductionStrategy());
        strategyMaps.put(Key.Rebate, new RebateStrategy());
    }

    private PromotionStrategyFactory() {

    }

    public static PromotionStrategy getPromotionStrategy(String key) {
        PromotionStrategy promotionStrategy = strategyMaps.get(key);
        return promotionStrategy == null ? NON_STRATEGY : promotionStrategy;
    }

    public interface Key {
        String CashBack = "CASH_BACK";
        String FullReduction = "FULL_REDUCTION";
        String Rebate = "REBATE";
    }
}
