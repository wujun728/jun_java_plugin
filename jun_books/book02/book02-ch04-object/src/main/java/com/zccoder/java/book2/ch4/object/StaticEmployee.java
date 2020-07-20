package com.zccoder.java.book2.ch4.object;

/**
 * <br>
 * 标题: 拥有静态域的对象<br>
 * 描述: 程序清单4-3<br>
 * 时间: 2018/11/07<br>
 *
 * @author zc
 */
public class StaticEmployee {

    private static int nextId = 1;

    /**
     * 名称
     */
    private String name;
    /**
     * 薪资
     */
    private Double salary;
    /**
     * 主键
     */
    private int id;

    public StaticEmployee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.id = 0;
    }

    public static void main(String[] args) {
        StaticEmployee employee = new StaticEmployee("哈利", 5000);
        System.out.println(employee);
    }

    public static int getNextId() {
        // 返回静态字段
        return nextId;
    }

    public void setId() {
        id = nextId;
        nextId++;
    }

    @Override
    public String toString() {
        return "StaticEmployee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }
}
