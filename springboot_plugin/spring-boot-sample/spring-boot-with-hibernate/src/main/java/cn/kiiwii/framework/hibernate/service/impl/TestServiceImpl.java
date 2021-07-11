package cn.kiiwii.framework.hibernate.service.impl;

import cn.kiiwii.framework.hibernate.dao.ITestDAO;
import cn.kiiwii.framework.hibernate.model.SpringBootWithHinernateTest;
import cn.kiiwii.framework.hibernate.model.SpringBootWithHinernateTest2;
import cn.kiiwii.framework.hibernate.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhong on 2016/9/5.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    @Autowired
    ITestDAO testDAO;

    @Override
    public void test() {
        this.testDAO.test();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testTransaction() throws Exception{
        SpringBootWithHinernateTest springBootWithHinernateTest = new SpringBootWithHinernateTest();
        springBootWithHinernateTest.setUserName("zhonglin1");
        this.testDAO.save(springBootWithHinernateTest);
        int i = 9 / 0;
        SpringBootWithHinernateTest2 springBootWithHinernateTest2 = new SpringBootWithHinernateTest2();
        springBootWithHinernateTest2.setUserName("zhonglin2");
        this.testDAO.save(springBootWithHinernateTest2);
    }
}
