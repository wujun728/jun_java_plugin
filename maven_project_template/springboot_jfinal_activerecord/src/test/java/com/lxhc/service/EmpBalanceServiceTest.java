package com.lxhc.service;

import com.lxhc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2020/5/21.
 *
 * @author 拎小壶冲
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EmpBalanceServiceTest {

    @Autowired
    private EmpBalanceService empBalanceService;

    @Test
    public void addBalanceTest() {
        empBalanceService.addBalance();
    }
}
