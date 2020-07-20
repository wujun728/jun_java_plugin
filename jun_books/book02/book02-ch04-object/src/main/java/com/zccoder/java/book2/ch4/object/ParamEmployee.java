package com.zccoder.java.book2.ch4.object;

/**
 * <br>
 * 标题: 简化的员工类<br>
 * 描述: 程序清单4-4<br>
 * 时间: 2018/11/07<br>
 *
 * @author zc
 */
public class ParamEmployee {
    private String name;
    private double salary;

    public ParamEmployee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    /**
     * 涨薪资
     *
     * @param byPercent 涨幅（单位：百分比）
     */
    public void raiseSalary(double byPercent) {
        double raise = this.salary * byPercent / 100;
        this.salary += raise;
    }

    @Override
    public String toString() {
        return "ParamEmployee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
