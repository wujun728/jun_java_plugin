package com.designpatterns.flyweight;

import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * 享元模式(会建立一个缓存池)
 * @author BaoZhou
 * @date 2018/12/29
 */
public class FlyweightTest {
    @Test
    void test() {
        String[] department = {"研发部", "测试部", "营销部", "管理部"};
        for (int i = 0; i < 20; i++) {
            Manager manager = (Manager) EmployeeFactory.getManager(department[new Random().nextInt(4)]);
            manager.setReportContent("报告内容啊");
            manager.report();
        }
    }
}
