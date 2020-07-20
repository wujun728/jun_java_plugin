package com.zccoder.java.book2.ch4.object;

import java.time.LocalDate;

/**
 * <br>
 * 标题: 员工<br>
 * 描述: 程序清单4-2<br>
 * 时间: 2018/11/06<br>
 *
 * @author zc
 */
public class Employee {
    /**
     * 名称
     */
    private String name;
    /**
     * 薪资
     */
    private Double salary;
    /**
     * 雇佣日期
     */
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    /**
     * 涨薪资
     *
     * @param byPercent 涨幅（单位：百分比）
     */
    public void raiseSalary(double byPercent) {
        Double raise = this.salary * byPercent / 100;
        this.salary += raise;
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}
