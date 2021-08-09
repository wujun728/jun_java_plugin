package cloud.simple.conf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cloud.simple.interfaces.*;
import cloud.simple.service.UserServiceImpl;

@Configuration
public class ThriftConfig {
	
	
	ExecutorService executor = Executors.newSingleThreadExecutor();

	@Bean
	public TServerTransport tServerTransport() {
		try {
			return new TServerSocket(7911);
		} catch (TTransportException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public TServer tServer() {
		//发布服务
		UserService.Processor processor = new UserService.Processor(
				new UserServiceImpl());
		TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
				tServerTransport()).processor(processor));
		return server;
	}
	
	@PostConstruct
	public void init(){
		executor.execute(new Runnable() {
			@Override
			public void run() {
				tServer().serve();	
			}
		});
	}

}
