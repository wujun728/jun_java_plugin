package com.jun.plugin.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jun.plugin.hibernate.modal.EmpEntity;

import java.util.List;

public class test1 {


    private Session ss;

    @Before
    public void beforeRun(){
        // 加载配置 初始化框架
        Configuration config = new Configuration().configure();
        // 获取会话工厂
        SessionFactory sf = config.buildSessionFactory();
        // 获取Session
        ss = sf.openSession();
    }

    @Test
    public void fun1(){

        EmpEntity e  =  ss.get(EmpEntity.class,new Short("7369"));
        System.out.println(e.getEmpName());

    }

    @Test
    public void fun2(){
        EmpEntity e = new EmpEntity();
        e.setEmpName("员工A");
        e.setEmpno(new Short("8080"));
        Transaction tc = ss.beginTransaction();
        ss.save(e);
        tc.commit();
    }

    @Test
    public void fun3(){
        EmpEntity e = new EmpEntity();
        e.setEmpName("员工B");
        e.setEmpno(new Short("8080"));
        Transaction tc = ss.beginTransaction();
        try {
            ss.update(e);
            tc.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            tc.rollback();
        }
    }

    @Test
    public void fun4(){
        String hql = "from EmpEntity";
        Query query = ss.createQuery(hql);
        List<EmpEntity> empList = query.list();
        for(EmpEntity e : empList){
            System.out.println(e.getEmpName());
        }
    }

    @After
    public void afterRun(){
        // 关闭Session
        ss.close();
    }
}
