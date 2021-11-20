package com.mycompany.myproject.base.lambda;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class LambadTest {

    Runnable r1 = () -> { System.out.println(this); };
    Runnable r2 = () -> { System.out.println(toString()); } ;
    Runnable r3 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    };
    Runnable r4 = new Runnable() {
        @Override
        public void run() {
            System.out.println(toString());
        }
    };
    public String toString() {  return "Hello, world"; }


    //对于给定的lambda表达式，它的类型是什么？答案是：它的类型是由其上下文推导而来
    public static void main(String[] args){

        //函数是接口
        test1(() -> System.out.println("MyFunctionalInterface lambda run "));


        //一元运算符
        UnaryOperator<Boolean> reverseB = (b)-> !b ;
        Boolean b = true;
        System.out.println(reverseB.apply(b));

        BinaryOperator<Integer> sumO =  (x,y) -> x+y;
        System.out.println(sumO.apply(10, 10));

        //lambda表达式并不是第一个拥有上下文相关类型的Java表达式：泛型方法调用和“菱形”构造器调用也通过目标类型来进行类型推导：
        List<String> ls = Collections.emptyList();
        List<Integer> li = Collections.emptyList();
        Map<String, Integer> m1 = new HashMap<>();
        Map<Integer, String> m2 = new HashMap<>();

        //6. 词法作用域（Lexical scoping）
        // 在内部类中使用变量名（以及this）非常容易出错。内部类中通过继承得到的成员（包括来自Object的方法）可能会把外部类的成员掩盖（shadow），
        // 此外未限定（unqualified）的this引用会指向内部类自己而非外部类。
        //相对于内部类，lambda表达式的语义就十分简单：它不会从超类（supertype）中继承任何变量名，也不会引入一个新的作用域。
        // lambda表达式基于词法作用域，也就是说lambda表达式函数体里面的变量和它外部环境的变量具有相同的语义（也包括lambda表达式的形式参数）。
        // 此外，'this'关键字及其引用在lambda表达式内部和外部也拥有相同的语义。
        new LambadTest().r1.run();
        new LambadTest().r2.run();
        Runnable r3_ = new LambadTest().r3;
        r3_.run();
        Runnable r4_ = new LambadTest().r4;
        r4_.run();
        System.out.println(r3_);
        System.out.println(r4_);

        //7. 变量捕获（Variable capture）
        String msg = "我是非final变量";
        new Runnable(){
            @Override
            public void run() {
                //msg = "ssss";  local variables referenced from an inner class must be final or effectively final
                System.out.println(msg);
            }
        }.run();

        Runnable r_  = ()->{
            //msg = "ssss";  //local variables referenced from a lambda expression must be final or effectively final
            System.out.println(msg);
        };

        // lambda表达式对值封闭，对变量开放的
        // 原文是：lambda expressions close over values, not variables，我在这里增加一个例子以说明这个特性：
        List<Integer> list = Arrays.asList(1,2,3,4);
        int sum = 0;
        //list.forEach(e -> { sum += e.size(); }); // Illegal, close over values

        List<Integer> aList = new ArrayList<>();
        list.forEach(e -> { aList.add(e); }); // Legal, open over variables

        //方法引用（Method references）
        Consumer<Integer> b1 = System::exit;    // void exit(int status)
        Consumer<String[]> b2 = Arrays::sort;    // void sort(Object[] a)
    }



    public static void test1(MyFunctionalInterface myFunctionalInterface){
        myFunctionalInterface.run();
    }

}
