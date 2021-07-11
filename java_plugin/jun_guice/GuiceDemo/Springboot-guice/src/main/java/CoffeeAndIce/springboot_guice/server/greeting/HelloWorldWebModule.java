package CoffeeAndIce.springboot_guice.server.greeting;

import com.google.inject.AbstractModule;

import CoffeeAndIce.GuiceDemo.hellowordDemo.MyApplet;
import CoffeeAndIce.GuiceDemo.hellowordDemo.MyDestination;
import CoffeeAndIce.GuiceDemo.hellowordDemo.StringWtringApplet;
import CoffeeAndIce.GuiceDemo.hellowordDemo.binds.OutPut;

public class HelloWorldWebModule extends AbstractModule {


	@Override
	public void configure() {
		bind(MyApplet.class).to(StringWtringApplet.class);
		bind(MyDestination.class).to(WebDestination.class);
		
		bind(String.class).annotatedWith(OutPut.class)
		.toProvider(GreetingMessageProvider.class);
	}
	
}
