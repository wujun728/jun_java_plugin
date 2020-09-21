/**
 * 
 */
package com.opensource.nredis.proxy.monitor.quartz.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.opensource.nredis.proxy.monitor.dao.IRedisClusterMasterDao;
import com.opensource.nredis.proxy.monitor.dao.IRedisClusterSlaveDao;
import com.opensource.nredis.proxy.monitor.enums.OperateStatusEnums;
import com.opensource.nredis.proxy.monitor.enums.StatusEnums;
import com.opensource.nredis.proxy.monitor.enums.TomcatStatusEnums;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMaster;
import com.opensource.nredis.proxy.monitor.model.RedisClusterSlave;
import com.opensource.nredis.proxy.monitor.quartz.IFrameworkQuartz;
import com.opensource.nredis.proxy.monitor.zookeeper.NRedisProxyZookeeper;
import com.opensource.nredis.proxy.monitor.zookeeper.enums.ZkNodeType;
import com.opensource.nredis.proxy.monitor.zookeeper.utils.ZkUtils;


/**
 * zookeeper定时器
 * @author liubing
 *
 */
@Service("zookeeperQuartz")
public class ZookeeperQuartzImpl implements IFrameworkQuartz {
	
	@Autowired
	private IRedisClusterMasterDao redisClusterMasterDao;
	
	@Autowired
	private IRedisClusterSlaveDao redisClusterSlaveDao ;
	
	@Autowired
	private NRedisProxyZookeeper redisProxyZookeeper;
	
	/* (non-Javadoc)
	 * @see com.opensource.nredis.proxy.monitor.quartz.IFrameworkQuartz#invoke(int)
	 */
	@Override
	public String invoke(int times) {
		String result="";
		try{
			List<String> masterDatas=redisProxyZookeeper.getAllChildren();
			for(String masterData:masterDatas){
				Integer masterId=oprerateMaster(masterData);
				if(masterId!=null&&masterId>0){
					List<String> slaveDatas=redisProxyZookeeper.getChildren(null, masterData);
					for(String slaveData:slaveDatas){
						oprerateSlave(slaveData, masterId,masterData);
					}
				}
				
				
			}
		}finally{
			result="调用成功";
		}
		
		return result;
	}
	
	/**
	 * 处理从
	 * @param slaveData
	 * @param masterId
	 * @return
	 */
	private void oprerateSlave(String slaveData,Integer masterId,String parentPath){
		String data=redisProxyZookeeper.getNodeData(parentPath, slaveData);
		JSONObject jsonObject =JSONObject.parseObject(data);
		
		RedisClusterSlave redisClusterSlave=this.getUniqueRedisClusterSlave(jsonObject.getString("host"), jsonObject.getInteger("port"));
		if(redisClusterSlave==null){//无机器
			redisClusterSlave=new RedisClusterSlave();
			redisClusterSlave.setCreateTime(new Date());
			redisClusterSlave.setVersion(1);
			redisClusterSlave.setRunnerStatus(TomcatStatusEnums.RUNNING.getCode());
			redisClusterSlave.setRedisServerStatus(StatusEnums.ENABLE.getCode());
			redisClusterSlave.setRedisServerHost(jsonObject.getString("host"));
			redisClusterSlave.setRedisServerPort(jsonObject.getInteger("port"));
			redisClusterSlave.setWeight(jsonObject.getInteger("weight"));
			redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
			redisClusterSlave.setRedisMasterId(masterId);
			String path=ZkUtils.toNodePath(parentPath, slaveData, ZkNodeType.AVAILABLE_SERVER);
			redisClusterSlave.setPath(path);
			redisClusterSlaveDao.insert(redisClusterSlave);
		}else{//机器从不可用变成可用
			if(redisClusterSlave.getRedisServerStatus()==StatusEnums.DISABLE.getCode()||redisClusterSlave.getRunnerStatus()==TomcatStatusEnums.STOP.getCode()){
				redisClusterSlave.setRunnerStatus(TomcatStatusEnums.RUNNING.getCode());
				redisClusterSlave.setRedisServerStatus(StatusEnums.ENABLE.getCode());
				redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
				redisClusterSlave.setVersion(1);
				String path=ZkUtils.toNodePath(parentPath, slaveData, ZkNodeType.AVAILABLE_SERVER);
				redisClusterSlave.setPath(path);
				redisClusterSlaveDao.updateById(redisClusterSlave);
			}
		}
	}
	
	/**
	 * 处理主服务
	 * @param childData
	 */
	private Integer oprerateMaster(String childData){
		
		String data=redisProxyZookeeper.getNodeData(null, childData);
		JSONObject jsonObject =JSONObject.parseObject(data);
		
		RedisClusterMaster oldRedisClusterMaster =this.getUniqueRedisClusterMaster(jsonObject.getString("host"), jsonObject.getInteger("port"));
		if(oldRedisClusterMaster==null){//机器不存在
			RedisClusterMaster redisClusterMaster=new RedisClusterMaster();
			redisClusterMaster.setRedisServerHost(jsonObject.getString("host"));
			redisClusterMaster.setRedisServerPort(jsonObject.getInteger("port"));
			redisClusterMaster.setCreateTime(new Date());
			redisClusterMaster.setVersion(1);
			redisClusterMaster.setRunnerStatus(TomcatStatusEnums.RUNNING.getCode());
			redisClusterMaster.setRedisServerStatus(StatusEnums.ENABLE.getCode());
			redisClusterMaster.setOprateStatus(OperateStatusEnums.DOING.getCode());			
			String path=ZkUtils.toNodePath(null, childData, ZkNodeType.AVAILABLE_SERVER);
			redisClusterMaster.setPath(path);
			redisClusterMasterDao.insert(redisClusterMaster);
			return redisClusterMaster.getId();
		}else{//机器存在，但是状态更新了，从不可用变成可用
			if(oldRedisClusterMaster.getRedisServerStatus()==StatusEnums.DISABLE.getCode()||oldRedisClusterMaster.getRunnerStatus()==TomcatStatusEnums.STOP.getCode()){
				oldRedisClusterMaster.setRunnerStatus(TomcatStatusEnums.RUNNING.getCode());
				oldRedisClusterMaster.setRedisServerStatus(StatusEnums.ENABLE.getCode());
				oldRedisClusterMaster.setOprateStatus(OperateStatusEnums.DOING.getCode());
				oldRedisClusterMaster.setVersion(1);
				String path=ZkUtils.toNodePath(null, childData, ZkNodeType.AVAILABLE_SERVER);
				oldRedisClusterMaster.setPath(path);
				redisClusterMasterDao.updateById(oldRedisClusterMaster);
				return oldRedisClusterMaster.getId();
			}
			
		}
		return null;
	}
	
	/**
	 * 获取唯一性 主redis server服务器
	 * @param host
	 * @param port
	 * @return
	 */
	private RedisClusterMaster getUniqueRedisClusterMaster(String host,Integer port){
		RedisClusterMaster redisClusterMaster=new RedisClusterMaster();
		redisClusterMaster.setRedisServerHost(host);
		redisClusterMaster.setRedisServerPort(port);
		List<RedisClusterMaster> redisClusterMasters=redisClusterMasterDao.getListByCriteria(redisClusterMaster);
		if(redisClusterMasters!=null&&redisClusterMasters.size()>0){
			return redisClusterMasters.get(0);
		}
		return null;
	}
	
	/**
	 * 获取唯一从redis server服务器
	 * @param host
	 * @param port
	 * @return
	 */
	private RedisClusterSlave getUniqueRedisClusterSlave(String host,Integer port){
		RedisClusterSlave redisClusterSlave=new RedisClusterSlave();
		redisClusterSlave.setRedisServerHost(host);
		redisClusterSlave.setRedisServerPort(port);
		List<RedisClusterSlave> redisClusterSlaves=redisClusterSlaveDao.getListByCriteria(redisClusterSlave);
		if(redisClusterSlaves!=null&&redisClusterSlaves.size()>0){
			return redisClusterSlaves.get(0);
		}
		return null;

	}
	
}
