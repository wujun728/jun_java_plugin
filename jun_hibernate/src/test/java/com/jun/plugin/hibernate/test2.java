package com.jun.plugin.hibernate;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.plugin.hibernate.modal.EmpEntity;
import com.jun.plugin.hibernate.service.IEmpService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-core.xml")
public class test2 {

    @Autowired
    private IEmpService empService;

    @Test
    public void fun1(){
        Integer count = empService.count();
        System.out.println(count);
    }

    @Test
    public void fun2(){
        List<EmpEntity> emps = empService.findList(2,5);
        System.out.println(emps.size());
    }


    @Test
    public void fun3(){
        EmpEntity e = new EmpEntity();
        e.setEmpName("员工BBB");
        e.setEmpno(new Short("8081"));
        empService.addEmp(e);
    }

    @Test
    public void fun5(){
        EmpEntity e = new EmpEntity();
        e.setEmpno(new Short("8081"));
        e.setEmpName("admin123");
        empService.updateEmp(e);
    }

}
