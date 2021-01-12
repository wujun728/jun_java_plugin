package com.designpatterns.builder.code;

/**
 * 建造者模式
 * idea可以通过innerBuilder插件自动生成
 * mybatis guava
 * @author BaoZhou
 * @date 2018/12/27
 */
public class Product {
    String name;
    String id;
    String color;
    Integer prize;

    private Product(Builder builder) {
        name = builder.name;
        id = builder.id;
        color = builder.color;
        prize = builder.prize;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", prize=" + prize +
                '}';
    }


    public static final class Builder {
        private String name;
        private String id;
        private String color;
        private Integer prize;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder color(String val) {
            color = val;
            return this;
        }

        public Builder prize(Integer val) {
            prize = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
