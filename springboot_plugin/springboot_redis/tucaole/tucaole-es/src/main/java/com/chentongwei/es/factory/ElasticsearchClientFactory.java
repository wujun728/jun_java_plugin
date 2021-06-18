package com.chentongwei.es.factory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.chentongwei.es.conf.Config;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: ES客户端工厂
 */
@Component
public class ElasticsearchClientFactory {
	
	@Autowired
	private Config config;

	private TransportClient client = null;

	/**
	 * 获取TransportClient
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public TransportClient client() {
		try {
			if(null == client) {
				Settings settings = Settings.builder()
						.put("client.transport.sniff", true)
						.put("cluster.name", config.getClusterName()).build();
				
				client = new PreBuiltTransportClient(settings)
					.addTransportAddress(
							new InetSocketTransportAddress(
									InetAddress.getByName(config.getIp()), config.getPort()));
			}
			return client;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
