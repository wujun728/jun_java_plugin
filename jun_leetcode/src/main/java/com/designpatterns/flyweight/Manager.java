package com.designpatterns.flyweight;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class Manager implements Employee {
    String department;
    String reportContent;

    public Manager(String department) {
        this.department = department;
    }

    @Override
    public void report() {
        System.out.println("我是 "+department+" 的部门经理"+"我的报告内容是： "+ reportContent);
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

}
