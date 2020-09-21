/*   
 * Project: OSMP
 * FileName: JvmMonitorProcess.java
 * version: V1.0
 */
package com.osmp.monitor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.osmp.monitor.util.JvmUtils;

/**
 * Description: JVM监控
 * 
 * @author: wangkaiping
 * @date: 2015年4月28日 下午3:14:21
 */
public class JvmMonitorProcess implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		double memTotal = JvmUtils.getMemoryTotal();
		double memUse = JvmUtils.getMemoryUse();
		double cpuUse = JvmUtils.getCpuRate();
		long current = System.currentTimeMillis();
		Message message = exchange.getOut();
		message.setHeader(Exchange.HTTP_QUERY, "memTotal=" + memTotal + "&memUse=" + memUse + "&cpuUse=" + cpuUse
				+ "&time=" + current);
	}

}
