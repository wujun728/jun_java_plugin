package com.java8.stream;



import com.java8.lambda.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BaoZhou
 * @date 2018/7/30
 */
public class StreamTest02 {

    /**
     * reduce 归约
     */
    @Test
    void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );

        Optional<Integer> reduce = employees.stream().map(Employee::getAge).reduce(Integer::sum);
        System.out.println(reduce.get());
    }


    /**
     * 收集 collect
     */
    @Test
    void test2() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );
        System.out.println("-------------------------------------------------------");
        //放入新集合
        List<String> collect = employees.stream().map(Employee::getName).collect(Collectors.toList());
        collect.forEach(System.out::println);

        System.out.println("-------------------------------------------------------");
        //放入新集合 别的容器
        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new))
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------");
        //算总数
        Long collect1 = employees.stream()
                .collect(Collectors.counting());
        System.out.println(collect1);
        System.out.println("-------------------------------------------------------");
        //分组
        Map<Integer, List<Employee>> collect2 = employees.stream().collect(Collectors.groupingBy(Employee::getAge));
        System.out.println(collect2);
        System.out.println("-------------------------------------------------------");
        //多级分组
        Map<Integer, Map<String, List<Employee>>> collect3 = employees.stream()
                .collect(Collectors
                        .groupingBy(Employee::getAge, Collectors.groupingBy(Employee::getName)));
        System.out.println(collect3);
        System.out.println("-------------------------------------------------------");
        //分区
        Map<Boolean, List<Employee>> collect4 = employees.stream().collect(Collectors.partitioningBy(e -> e.getAge() > 18));
        System.out.println(collect4);
        System.out.println("-------------------------------------------------------");
        //统计
        IntSummaryStatistics collect5 = employees.stream().collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println(collect5.getMax());
        System.out.println(collect5.getAverage());
        System.out.println("-------------------------------------------------------");
        //拼接
        String collect6 = employees.stream().map(Employee::getName).collect(Collectors.joining(",", "=====", "======"));
        System.out.println(collect6);
    }

    @Test
    void exercise() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream().map(integer -> integer * integer).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("-------------------------------------------------------");
        int[] ints = {1, 2, 3, 4, 5};
        Arrays.stream(ints)
                .map(x -> x * x)
                .forEach(System.out::println);
        ;
        System.out.println("-------------------------------------------------------");
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );
        Optional<Integer> reduce = employees.stream().map(e -> 1).reduce(Integer::sum);
        System.out.println(reduce.orElse(0));
        System.out.println("-------------------------------------------------------");
    }
}
