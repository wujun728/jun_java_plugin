package com.ic911.htools.service.hadoop;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.NodeMonitorInterceptor;
/**
 * 系统的启动器，第一次启动会做一些初始化
 * @author caoyang
 */
public class ThreadStart {
	private static final Logger logger = LoggerFactory.getLogger(ThreadStart.class);
	@Autowired
	private MasterConfigService masterConfigService;
	@Autowired
	private NodeMonitorInterceptor interceptor; 
	
	public void start(){
		Collection<MasterConfig> configs = masterConfigService.findAll();
		if(configs!=null){
			for(MasterConfig config : configs){
				if(config.getEnabled()){
					HadoopClusterServer.putMasterConfig(config);
				}
			}
		}
		NodeMonitor.setInterceptor(interceptor);
		NodeMonitor.startup();
		logger.info("ThreadStart已经启动相关程序!");
	}

	public void setInterceptor(NodeMonitorInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
}
