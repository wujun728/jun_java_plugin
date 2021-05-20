package com.tanghd.spring.dbutil.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tanghd.spring.dbutil.aop.DataSourceChange;

public class TestAspect {

    private JdbcTemplate jdbcTemplate;

    @DataSourceChange(slave = true)
    public void test1() {
        System.out.println(jdbcTemplate.queryForList("select * from test where id=1"));
    }

    @DataSourceChange(slave = true)
    public void test2() {
        System.out.println(jdbcTemplate.queryForList("select * from test where id=1"));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test_aspect.xml");
        TestAspect t = context.getBean(TestAspect.class);
        t.test1();
        System.out.println("----------------------------------------");
        t.test2();
    }
}
