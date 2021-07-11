package CoffeeAndIce.springboot_guice.server.greeting;

import com.google.inject.servlet.RequestScoped;

import CoffeeAndIce.GuiceDemo.hellowordDemo.MyDestination;

@RequestScoped
public class WebDestination implements MyDestination {
	private final StringBuilder sb;

	public WebDestination() {
		System.out.println("WebDestination constroct");
		this.sb = new StringBuilder();
	}

	@Override
	public void write(String string) {
		sb.append(string);
	}

	public String getResult() {
		return sb.toString();
	}

}
