package com.zccoder.java.book2.ch4.object;

/**
 * <br>
 * 标题: 方法参数传递测试<br>
 * 描述: 程序清单4-4<br>
 * 时间: 2018/11/07<br>
 *
 * @author zc
 */
public class ParamTest {

    public static void main(String[] args) {

        // 测试1：方法不能修改基本数据类型
        System.out.println("测试基本数据类型");
        double percent = 10;
        System.out.println("调用前：percent=" + percent);
        tripleValue(percent);
        System.out.println("调用后：percent=" + percent);

        // 测试2：方法可以修改对象状态
        System.out.println("测试修改对象状态");
        ParamEmployee harry = new ParamEmployee("Harry", 50000);
        System.out.println("调用前：harry=" + harry);
        tripleSalary(harry);
        System.out.println("调用后：harry=" + harry);

        // 测试3：方法不能修改入参对象的引用
        System.out.println("测试交换两个对象的引用");
        ParamEmployee a = new ParamEmployee("Alice", 7000);
        ParamEmployee b = new ParamEmployee("Bob", 6000);
        System.out.println("调用前：a=" + a);
        System.out.println("调用前：b=" + b);
        swap(a, b);
        System.out.println("调用后：a=" + a);
        System.out.println("调用后：b=" + b);

    }

    private static void swap(ParamEmployee x, ParamEmployee y) {
        ParamEmployee temp = x;
        x = y;
        y = temp;
        System.out.println("End od Method : x =" + x);
        System.out.println("End od Method : y =" + y);
    }

    private static void tripleSalary(ParamEmployee employee) {
        employee.raiseSalary(200);
        System.out.println("End od Method : x =" + employee);
    }

    private static void tripleValue(double value) {
        value = 3 * value;
        System.out.println("End of Method : x = " + value);
    }

}
