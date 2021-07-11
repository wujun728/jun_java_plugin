package CoffeeAndIce.springboot_guice.server.greeting;

import javax.inject.Inject;

import com.google.inject.servlet.RequestScoped;

import CoffeeAndIce.GuiceDemo.hellowordDemo.MyApplet;

@RequestScoped
public class GreetingHandler {
	private final MyApplet applet;
	private final WebDestination destination;
	private final RequestParams params;
	
	@Inject
	public GreetingHandler(MyApplet applet, WebDestination destination, RequestParams requestParams) {
		super();
		this.applet = applet;
		this.destination = destination;
		this.params = requestParams;
	}

	public String getByName(String name) {
		params.setGreetingName(name);
		applet.run();
		return destination.getResult();
	}

}
