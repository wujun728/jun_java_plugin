package CoffeeAndIce.springboot_guice.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.google.inject.Guice;
import com.google.inject.Injector;

import CoffeeAndIce.GuiceDemo.hellowordDemo.MyApplet;
import CoffeeAndIce.springboot_guice.server.SpringScanBase;
import CoffeeAndIce.springboot_guice.server.greeting.GreetingHandler;
import CoffeeAndIce.springboot_guice.server.greeting.HelloWorldWebModule;
import CoffeeAndIce.springboot_guice.server.greeting.WebDestination;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication(scanBasePackageClasses = SpringScanBase.class)
@ServletComponentScan
public class SimpleController {
	
	@Bean 
	Injector injector(ApplicationContext context){//每一个@Bean下面，都有默认注入，如果有参数。
		return Guice.createInjector(
				new HelloWorldWebModule(),
				new SpringAwareServletModule(context));
	}
	
	
	@Bean @RequestScope
	WebDestination desdestination(Injector injector){
		return injector.getInstance(WebDestination.class);
	}
	
	@Bean @RequestScope
	MyApplet applet(Injector injector){
		return injector.getInstance(MyApplet.class);
	}
	
	@Bean @RequestScope
	GreetingHandler greetingHandler(Injector injector){
		return injector.getInstance(GreetingHandler.class);
	}
	

	@Autowired GreetingHandler greetingHandler;
    @GetMapping("/greeting")
    String home(@RequestParam("name") String name) {
    	return greetingHandler.getByName(name);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleController.class, args);
    }
}
