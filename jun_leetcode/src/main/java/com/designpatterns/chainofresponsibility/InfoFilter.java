package com.designpatterns.chainofresponsibility;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class InfoFilter extends AbstractFilter {


    @Override
    protected void doFilter(Product product) {
        if(product.getInfo().isEmpty() ){
            System.out.println("商品信息丢失");
        }
    }
}
