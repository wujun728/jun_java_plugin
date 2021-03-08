package io.springside.springtime.examples.helloservice.idl;

public interface GreetingService {

	public static class HelloRequest {
		public String name;
	}

	public static class HelloResponse {
		public String message;
	}

	public static class WeatherRequest {
		public City city;
	}

	public static class WeatherResponse {
		public String weather;
	}

	public HelloResponse hello(HelloRequest helloRequest);

	public WeatherResponse weather(WeatherRequest weatherRequest);

}
