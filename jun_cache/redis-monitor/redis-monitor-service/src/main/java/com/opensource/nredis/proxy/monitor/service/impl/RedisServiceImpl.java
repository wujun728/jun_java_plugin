/**
 * 
 */
package com.opensource.nredis.proxy.monitor.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSONObject;
import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.model.RedisConfigInfo;
import com.opensource.nredis.proxy.monitor.service.IRedisService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;

/**
 * @author liubing
 *
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService {
	
	/* (non-Javadoc)
	 * @see com.opensource.nredis.proxy.monitor.service.IRedisService#getRedisConfigInfo(java.lang.String)
	 */
	@SuppressWarnings("resource")
	@Override
	public RedisConfigInfo getRedisConfigInfo(String host,int port) {
		Jedis jedis=new Jedis(host, port, 5000);
		String result=jedis.info();
		if(StringUtil.isNotEmpty(result)){
			String[] datas=result.split("\r\n");
			JSONObject jsonObject=new JSONObject();
			for(String data:datas){				
				if(StringUtil.isNotEmpty(data)&&!data.contains("#")&&data.contains(":")){
					String[] values=data.split(":");
					if(values[0].equals("connected_clients")){
						jsonObject.put("clientConnection", Integer.parseInt(values[1]));
					}else if(values[0].equals("total_commands_processed")){
						jsonObject.put("totalCommands", Integer.parseInt(values[1]));
					}else if(values[0].equals("used_memory_peak_human")){
						jsonObject.put("sysMemory", values[1].substring(0, values[1].length()-1));
					}else if(values[0].equals("used_memory_human")){
						jsonObject.put("usedMemory", values[1].substring(0, values[1].length()-1));
					}else if(values[0].equals("keyspace_hits")){
						jsonObject.put("keyHits", Double.parseDouble(values[1]));
					}else if(values[0].equals("keyspace_misses")){
						jsonObject.put("keyMiss", Double.parseDouble(values[1]));
					}else if(values[0].equals("used_cpu_sys")){
						jsonObject.put("usedSystemCPU", Double.parseDouble(values[1]));
					}else if(values[0].equals("used_cpu_user")){
						jsonObject.put("usedHumanCPU", Double.parseDouble(values[1]));
					}
				}
			}
			if(!jsonObject.isEmpty()){
				jsonObject.put("timestamp", DateBase.formatDate(new Date(), DateBase.DATE_PATTERN_TIME));

				RedisConfigInfo redisConfigInfo=JSONObject.parseObject(jsonObject.toJSONString(), RedisConfigInfo.class);
				return redisConfigInfo;
			}
			
		}
		jedis.close();
		return null;
	}

}
