package com.jun.plugin.springservlet3.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.springservlet3.config.ServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ServiceConfig.class)
public abstract class BaseServiceTest {
}
