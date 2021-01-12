package com.java8.stream;


import com.java8.lambda.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author BaoZhou
 * @date 2018/7/30
 */
public class StreamTest01 {
    /**
     * 创建Stream
     * stream 串行
     * parallelStream 并行
     */
    @Test
    public void test1() {
        //集合转Stream
        List<String> list = new ArrayList<>();
        list.stream();
        //Arrays中的静态方法steam()获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream = Arrays.stream(emps);

        //使用静态方法of
        Stream.of("123", "1111", "aaaa");


        //创造无限流
        //迭代
        Stream.iterate(0, x -> x + 2)
                .limit(10)
                .forEach(System.out::println);
        //生成
        Stream.generate(() -> new Random().nextInt(100))
                .limit(5)
                .forEach(System.out::println);
    }

    /**
     * 筛选与切片
     * <p>
     * filter - 接收lambda,从流中排除某些元素
     * limit - 截断流，使元素不超过指定数量
     * skip(n) - 跳过元素，返回一个扔掉了前n个元素的流，若流中的元素不足n个
     * distinct - 筛选，通过流所生成元素的hashCode()和equals()去除重复元素
     * <p>
     * 在没有截断操作出现时，这些中间操作不会执行
     * <p>
     * map 映射，把元素映射成一个新的你需要的元素
     * flatMap 映射，把元素组成的集合映射进来
     * 类比
     * map 与 flatMap 的区别可以类比 add 与 addAll
     */
    @Test
    public void test2() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );
        Stream<Employee> stream = employees.stream();
        stream.filter(employee ->
        {
            System.out.println("中间操作");
            return employee.getAge() > 2;
        })
                .limit(4)
                .skip(1)
                .distinct()
                //终结操作
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------");
        //使用Map，只输出名字
        Stream<Employee> stream1 = employees.stream();
        stream1.map(Employee::getName)
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------");

        List<String> testString = Arrays.asList(
                "guard", "prince", "save", "lord", "king", "queen", "knight");
        //使用flatMap 输出名字中的每一个字符
        Stream<String> stream2 = testString.stream();
        stream2.flatMap(str->
        {
            List<Character> characters = new ArrayList<>();
            for(Character ch:str.toCharArray())
            {
                characters.add(ch);
            }
            return characters.stream();
        })
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------");
    }

    /**
     * sort 排序
     */
    @Test
    void test3()
    {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );
        Stream<Employee> stream = employees.stream();
        stream.sorted((e1,e2)->
        {
            if(e1.getAge().equals(e2.getAge()))
            {
                return e1.getName().compareTo(e2.getName());
            }
            else
            {
                return Integer.compare(e1.getAge(),e2.getAge());
            }
        })
                .forEach(System.out::println);
    }

    /**
     *
     * 查找与匹配
     * allMatch
     * anyMatch
     * noneMatch
     * findFirst
     * findAny
     * count
     * max
     * min
     */
    @Test
    void test4()
    {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18),
                new Employee("张三", 22),
                new Employee("啊一", 16),
                new Employee("毛呢", 22),
                new Employee("可爱", 10),
                new Employee("可爱", 10)
        );
        Stream<Employee> stream = employees.stream();
        boolean b = stream.allMatch(employee -> employee.getAge().equals(22));
        System.out.println(b);
        Stream<Employee> stream2 = employees.stream();
        Optional<Employee> first = stream2.filter(employee -> employee.getAge()>100).findFirst();
        System.out.println(first.orElse(new Employee("可爱", 10)));
    }
}
