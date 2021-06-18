package org.smartboot.integration.test;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Rollback(false)
@ImportResource("classpath*:service-integration.xml")
@SpringBootTest
@SpringBootApplication(
	scanBasePackages = { "org.smartboot" })
public abstract class AbstractUnitTest {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AbstractUnitTest.class, args);
	}
}