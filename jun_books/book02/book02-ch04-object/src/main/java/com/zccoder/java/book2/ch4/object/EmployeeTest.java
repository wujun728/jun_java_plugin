package com.zccoder.java.book2.ch4.object;

/**
 * <br>
 * 标题: 员工测试类<br>
 * 描述: 程序清单4-2<br>
 * 时间: 2018/11/06<br>
 *
 * @author zc
 */
public class EmployeeTest {

    public static void main(String[] args) {
        Employee[] employees = new Employee[3];

        employees[0] = new Employee("张三", 50000, 2018, 10, 1);
        employees[1] = new Employee("李四", 60000, 2018, 10, 2);
        employees[2] = new Employee("王五", 70000, 2018, 10, 3);

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // 每个人的薪资涨 5%
        for (Employee employee : employees) {
            employee.raiseSalary(5);
        }

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

}
