package com.zccoder.java.book2.ch4.object;

/**
 * <br>
 * 标题: 静态方法测试<br>
 * 描述: 程序清单4-3<br>
 * 时间: 2018/11/07<br>
 *
 * @author zc
 */
public class StaticTest {

    public static void main(String[] args) {
        StaticEmployee[] staff = new StaticEmployee[3];

        staff[0] = new StaticEmployee("张三", 5000);
        staff[1] = new StaticEmployee("李四", 6000);
        staff[2] = new StaticEmployee("王五", 7000);

        for (StaticEmployee employee : staff) {
            employee.setId();
            System.out.println(employee);
        }

        // 类静态方法
        int nextId = StaticEmployee.getNextId();
        System.out.println("下一个可用的ID=" + nextId);
    }

}
