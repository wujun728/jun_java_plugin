package com.designpatterns.chainofresponsibility;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class NameFilter extends AbstractFilter {


    @Override
    protected void doFilter(Product product) {
        if(product.getName().isEmpty() ){
            System.out.println("商品名称丢失");
        }
    }
}
