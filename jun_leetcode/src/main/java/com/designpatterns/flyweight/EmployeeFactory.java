package com.designpatterns.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class EmployeeFactory {
    private static final Map<String, Employee> EMPLOYEE_MAP = new HashMap<>();

    public static Employee getManager(String department) {
        Manager manager = (Manager) EMPLOYEE_MAP.get(department);
        if (manager == null) {
            manager = new Manager(department);
            EMPLOYEE_MAP.put(department, manager);
            System.out.println("创建了一个新的 " + department + " 成员");
        }
        return manager;

    }

}
