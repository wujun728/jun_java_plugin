package CoffeeAndIce.springboot_guice.server.greeting;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class RequestParams {
	private String greetingName;

	
	public RequestParams() {
		System.out.println("RequestParams constroct");
	}


	public String getGreetingName() {
		return greetingName;
	}


	public void setGreetingName(String greetingName) {
		this.greetingName = greetingName;
	}


}
