package com.lxhc.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lxhc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2020/5/19.
 *
 * @author 拎小壶冲
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EmpTest {

    @Test
    public void masterEmpTest() {
        Emp emp = Emp.dao.findById(1);
        System.out.println(emp.getStr("name"));

        Record emp2 = Db.use("biz").findFirst("select * from emp where id = 1");
        System.out.println(emp2.getStr("name"));

        Emp emp3 = new Emp().use("biz").findById(1);
        System.out.println(emp3.getStr("name"));
    }
}
