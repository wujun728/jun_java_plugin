package io.springside.springtime.examples.helloservice;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * via HTTP 1.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class GreetingServerWithApacheTest {

	@Test
	public void helloService() throws ClientProtocolException, IOException {
		String requestJson = "{\"name\":\"david\"}";
		String expectdResponse = "{\"message\":\"Hello david\"}";
		String response = Request.Post("http://localhost:8080/rpc/greeting/hello").version(HttpVersion.HTTP_1_1)
				.bodyString(requestJson, ContentType.APPLICATION_JSON).execute().returnContent().asString();
		assertThat(response).isEqualTo(expectdResponse);
	}

	@Test
	public void weatherService() throws ClientProtocolException, IOException {
		String requestJson = "{\"city\":{\"name\":\"guangzhou\"}}";
		String expectdResponse = "{\"weather\":\"Sunny at guangzhou\"}";

		String response = Request.Post("http://localhost:8080/rpc/greeting/weather").version(HttpVersion.HTTP_1_1)
				.bodyString(requestJson, ContentType.APPLICATION_JSON).execute().returnContent().asString();
		assertThat(response).isEqualTo(expectdResponse);
	}

	@Test
	public void helloRest() throws ClientProtocolException, IOException {
		String expectdResponse = "Hello World!";
		String response = Request.Get("http://localhost:8080/web/hello").version(HttpVersion.HTTP_1_1).execute()
				.returnContent().asString();
		assertThat(response).isEqualTo(expectdResponse);
	}
}
