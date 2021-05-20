package org.smartboot.dal.test;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(false)
@Transactional(
	transactionManager = "transactionManager")
@SpringBootTest
@SpringBootApplication(
	scanBasePackages = { "org.smartboot" })
public abstract class AbstractUnitTest {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AbstractUnitTest.class, args);
	}
}