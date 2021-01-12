package com.designpatterns.chainofresponsibility;

import org.junit.jupiter.api.Test;

/**
 * 责任链模式
 * @author BaoZhou
 * @date 2019/1/4
 */
public class ChainofResponsibilityTest {

    @Test
    void test() {
        AbstractFilter infoFilter = new InfoFilter();
        AbstractFilter nameFilter = new NameFilter();
        AbstractFilter priceFilter = new PriceFilter();
        infoFilter.setNextLogger(priceFilter);
        nameFilter.setNextLogger(infoFilter);
        nameFilter.Filter(new Product("","11",10));
    }
}
