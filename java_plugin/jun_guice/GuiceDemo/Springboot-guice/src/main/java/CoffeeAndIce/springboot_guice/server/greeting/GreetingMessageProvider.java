package CoffeeAndIce.springboot_guice.server.greeting;

import javax.inject.Inject;
import javax.inject.Provider;

import CoffeeAndIce.springboot_guice.server.persitence.SimpleDao;

public class GreetingMessageProvider implements Provider<String>{
	private final RequestParams requestParams;
	private final SimpleDao dao;
	
	@Inject
	public GreetingMessageProvider(
			RequestParams requestParams,
			SimpleDao dao) {
		this.requestParams = requestParams;
		this.dao = dao;
	}

	public String getGreetingMessage() {
		return "Hello:"+requestParams.getGreetingName();
	}

	@Override
	public String get() {
		String greetingName = requestParams.getGreetingName();
		dao.getPersonData(greetingName);
		return "Hello:"+greetingName;
	}

}
