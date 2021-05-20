package boot.spring.elastic.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Client {
	
	@Bean
	RestHighLevelClient configRestHighLevelClient(){
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("172.16.3.151", 9200, "http"),
		                new HttpHost("172.16.3.152", 9200, "http"),
		                new HttpHost("172.16.3.153", 9200, "http")));
		return client;
	}
}
