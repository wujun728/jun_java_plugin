package cloud.simple.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Configuration;



@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableTurbine
public class TurbineApplication {

	public static void main(String[] args) {
		 SpringApplication.run(TurbineApplication.class, args);
		//boolean cloudEnvironment = new StandardEnvironment().acceptsProfiles("cloud");
		//new SpringApplicationBuilder(TurbineApplication.class).web(!cloudEnvironment).run(args);
	}
}
