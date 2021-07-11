package cn.kiiwii.framework.hibernate.dao.impl;

import cn.kiiwii.framework.hibernate.dao.ITestDAO;
import cn.kiiwii.framework.hibernate.model.SpringBootWithHinernateTest;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2016/9/5.
 */
@Repository("testDAO")
public class TestDAOImpl extends HibernateDaoSupport implements ITestDAO {

    @Autowired
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void test() {
        SpringBootWithHinernateTest springBootWithHinernateTest = this.getHibernateTemplate().get(SpringBootWithHinernateTest.class,1);
        System.out.println(springBootWithHinernateTest);
    }

    @Override
    public void save(Object o) {
        this.getHibernateTemplate().save(o);
    }
}
