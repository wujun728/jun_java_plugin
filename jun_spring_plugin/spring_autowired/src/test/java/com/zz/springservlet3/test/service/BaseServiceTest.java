package com.zz.springservlet3.test.service;

import com.zz.springservlet3.config.ServiceConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ServiceConfig.class)
public abstract class BaseServiceTest {
}
