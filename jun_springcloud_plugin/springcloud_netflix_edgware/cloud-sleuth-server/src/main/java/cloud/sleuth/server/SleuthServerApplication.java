package cloud.sleuth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SleuthServerApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SleuthServerApplication.class,args);
    }
}
	