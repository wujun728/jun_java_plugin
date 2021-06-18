package org.smartboot.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(
	scanBasePackages = { "org.smartboot" })
@ImportResource(
	locations = { "classpath*:service-config.xml", "classpath*:mvc-config.xml" })
public class BootStrap {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootStrap.class, args);
	}
}
