package com.designpatterns.chainofresponsibility;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class PriceFilter extends AbstractFilter {


    @Override
    protected void doFilter(Product product) {
        if(product.getPrice()<10 ){
            System.out.println("商品定价过低");
        }
    }
}
