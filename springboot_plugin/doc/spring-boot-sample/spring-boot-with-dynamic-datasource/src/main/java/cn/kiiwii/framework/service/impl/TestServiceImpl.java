package cn.kiiwii.framework.service.impl;

import cn.kiiwii.framework.dao.ITestDAO;
import cn.kiiwii.framework.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2016/9/5.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    @Autowired
    ITestDAO testDAO;

    @Override
    public void test() {
        this.testDAO.testMaster();
        this.testDAO.testSlave1();
        this.testDAO.testSlave2();
    }
}
