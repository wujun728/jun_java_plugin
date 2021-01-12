package com.java8.lambda;



import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author: BaoZhou
 * @date : 2018/7/29 23:22
 */
public class LambdaTest {
    /**
     * JAVA8 内置的四大核心函数式接口
     * Consumer<T> : 消费型接口 void accpet<T t>
     * Supplier<T> ：供给型接口 T get();
     * Function<T, R> 函数型接口 R apply(T t);
     * Predicate<T> ：断言型接口 boolean test(T t);
     *
     * 还有许多子类型，比如多参数输入
     */
    @Test
    public void test0() {
        buy(5000, n -> System.out.println(n));

        List<Integer> list = getList(10, () -> new Random().nextInt(100));
        System.out.println(list);

        String text = strModify("test", str -> str.toUpperCase().substring(1, 3));
        System.out.println(text);

        isRich(10000, n -> n > 8000);
    }

    public void buy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    public List<Integer> getList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    public String strModify(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    public void isRich(double money, Predicate<Double> predicate) {
        if (predicate.test(money)) {
            System.out.println("有钱人");
        } else {
            System.out.println("穷逼");
        }

    }

    /**
     * 练习
     */
    @Test
    public void test1() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("收到", 22),
                new Employee("啊一", 22),
                new Employee("毛呢", 22),
                new Employee("可爱", 10)
        );

        Collections.sort(employees, (e1, e2) ->
        {
            if (e1.getAge().equals(e2.getAge())) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });

        Collections.sort(employees, Comparator.comparingInt(Employee::getAge));
        employees.stream().forEach(System.out::println);
    }


}
