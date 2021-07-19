package com.buxiaoxia;

import com.buxiaoxia.system.utils.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private Sender sender;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... strings) throws Exception {
		sender.send1("sender1");
		sender.send2("sender2");
	}
}
