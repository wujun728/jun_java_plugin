package com.jun.plugin.springservlet3.test.dao;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.springservlet3.config.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = PersistenceConfig.class)
public abstract class BaseDaoTest {
}
