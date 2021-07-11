package cn.kiiwii.framework.service.impl;

import cn.kiiwii.framework.dao.ITestDAO;
import cn.kiiwii.framework.service.ITestService;
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

    @Transactional
    @Override
    public void testTransaction() throws Exception{
        this.testDAO.save();
        int i = 9/0;
        this.testDAO.save();
    }
}
