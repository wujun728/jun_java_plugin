package com.zccoder.java.book1.ch5.block;

import java.io.Serializable;

/**
 * 标题：产品信息<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -5837748935004836713L;

    private String name;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
