package com.mycompany.myproject.base.lambda;


import java.util.function.Consumer;

// 消费型接口  输出一个值
public class ConsumerTest {

    public static void donation(Integer money, Consumer<Integer> consumer){
        consumer.accept(money);
    }


    public static void main(String[] args){

        donation(1000, (money)-> System.out.println("好心的麦乐迪为Blade捐赠了"+money+"元"));

    }

}
