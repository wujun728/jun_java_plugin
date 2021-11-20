package com.mycompany.myproject.base.lambda;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaTest {

    public static void main(String[] args){

        //用lambda表达式实现Runnable
        test1();
        //使用Java 8 lambda表达式进行事件处理
        test2();
        //例3、使用lambda表达式对列表进行迭代
        test3();
        //例4、使用lambda表达式和函数式接口Predicate
        test4();
        //例5、如何在lambda表达式中加入Predicate
        test5();
        //例6、Java 8中使用lambda表达式的Map和Reduce示例
        test6();
        //例7、通过过滤创建一个String列表
        test7();
        //例8、对列表的每个元素应用函数
        test8();
        //例9、复制不同的值，创建一个子列表
        test9();
        //例10、计算集合元素的最大值、最小值、总和以及平均值
        test10();

    }



    public static void test1(){

        Runnable runnable = () -> System.out.println("xian cheng a qin");
        new Thread(runnable, "MyThread").start();

    };

    public static void test2(){

    };
    public static void test3(){
        Arrays.asList(1,2,3,4,5,6,7,8).forEach(System.out::println);
    };
    public static void test4(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4);

    };
    public static void test5(){

        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        // 甚至可以用and()、or()和xor()逻辑函数来合并Predicate，
        // 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        languages.stream().filter(startsWithJ.and(fourLetterLong))
                .forEach(System.out::println);
    };
    public static void test6(){

        // 使用lambda表达式 为每个订单加上12%的税
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream().map((cost) -> cost + 0.12 * cost).forEach(System.out::println);

        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost)
                .reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    };
    public static void test7(){

        // 创建一个字符串列表，每个字符串长度大于2
        List<String> strList = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        List<String> filtered = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    };
    public static void test8(){
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    };
    public static void test9(){
        // 用所有不同的数字创建一个正方形列表
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    };
    public static void test10(){
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    };



    public static void filter(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
        System.out.println(name + " ");
        });
    }
}
