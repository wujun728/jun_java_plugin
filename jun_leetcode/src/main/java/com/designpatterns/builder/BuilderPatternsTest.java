package com.designpatterns.builder;

import com.designpatterns.builder.code.Product;
import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class BuilderPatternsTest {
    /**
     * 建造者模式
     */
    @Test
    public void Builder() {
        Product product = new Product.Builder()
                .name("可乐")
                .color("褐色")
                .id("12138")
                .prize(3).build();
        System.out.println(product);
    }
}
