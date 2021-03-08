package com.tanghd.spring.dbutil.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tanghd.spring.dbutil.aop.DataSourceChange;

public class TestAdvisor {

    private JdbcTemplate jdbcTemplate;

    @DataSourceChange(slave = true)
    public void test1() {
        System.out.println(jdbcTemplate.queryForList("select * from test where id=1"));
    }

    @DataSourceChange(slave = true)
    public void test2() {
        System.out.println(jdbcTemplate.queryForList("select * from test where id=1"));
    }

    @DataSourceChange(slave = true)
    public void testThrowing() throws Exception {
        System.out.println(jdbcTemplate.queryForList("select * from test where id=1"));
        throw new Exception("testThrowing");
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test_advisor.xml");
        TestAdvisor t = context.getBean(TestAdvisor.class);
        t.test1();
        System.out.println("----------------------------------------");
        t.test2();
        System.out.println("----------------------------------------");
        try {
            t.testThrowing();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
