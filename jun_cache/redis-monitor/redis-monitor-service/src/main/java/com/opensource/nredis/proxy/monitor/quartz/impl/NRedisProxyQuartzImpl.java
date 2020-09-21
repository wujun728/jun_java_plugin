/**
 * 
 */
package com.opensource.nredis.proxy.monitor.quartz.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;
import com.opensource.nredis.proxy.monitor.dao.IRedisClusterMasterDao;
import com.opensource.nredis.proxy.monitor.dao.IRedisClusterMonitorLogDao;
import com.opensource.nredis.proxy.monitor.dao.IRedisClusterSlaveDao;
import com.opensource.nredis.proxy.monitor.enums.OperateStatusEnums;
import com.opensource.nredis.proxy.monitor.enums.StatusEnums;
import com.opensource.nredis.proxy.monitor.enums.TomcatStatusEnums;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMaster;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMonitorLog;
import com.opensource.nredis.proxy.monitor.model.RedisClusterSlave;
import com.opensource.nredis.proxy.monitor.quartz.IFrameworkQuartz;
import com.opensource.nredis.proxy.monitor.zookeeper.NRedisProxyZookeeper;

/**
 * redis 主从检测是否健康
 * @author liubing
 *
 */
@Service("redisProxyQuartzImpl")
public class NRedisProxyQuartzImpl implements IFrameworkQuartz {
	
	@Autowired
	private IRedisClusterMasterDao redisClusterMasterDao;
	
	@Autowired
	private IRedisClusterSlaveDao redisClusterSlaveDao ;
	
	@Autowired
	private NRedisProxyZookeeper redisProxyZookeeper;
	
	@Autowired
	private IRedisClusterMonitorLogDao redisClusterMonitorLogDao;
	
	/* (non-Javadoc)
	 * @see com.opensource.nredis.proxy.monitor.quartz.IFrameworkQuartz#invoke(int)
	 */
	@Override
	public String invoke(int times) {
		String result="";
		try{
			checkMasterStatus(times);
		}finally{
			result="执行成功";
		}
		
		return result;
	}
	
	/**
	 * 检查主从服务器
	 * @param times
	 */
	private void checkMasterStatus(int times){
		List<RedisClusterMaster> redisClusterMasters=this.getRunningRedisClusterMasters();
		if(redisClusterMasters!=null){
			for(RedisClusterMaster redisClusterMaster:redisClusterMasters){
				redisClusterMaster.setOprateStatus(OperateStatusEnums.DONE.getCode());
				int operatetimes= this.updateRunningRedisClusterMaster(redisClusterMaster);
				if(operatetimes>0){//设置操作状态，防止并行别的服务器操作到
					redisClusterMaster.setVersion(redisClusterMaster.getVersion()+1);
					boolean flag=getRedisStatusFlag(redisClusterMaster.getRedisServerHost(), redisClusterMaster.getRedisServerPort(), times);
					if(flag){//主好
						redisClusterMaster.setOprateStatus(OperateStatusEnums.DOING.getCode());
						this.updateRunningRedisClusterMaster(redisClusterMaster);//一直执行完毕，下个执行
						checkSlaveStatus(times, redisClusterMaster);//检查主对应从服务器状态
					}else{//主挂了
						redisClusterMaster.setOprateStatus(OperateStatusEnums.DOING.getCode());
						redisClusterMaster.setRunnerStatus(TomcatStatusEnums.STOP.getCode());
						redisClusterMaster.setRedisServerStatus(StatusEnums.DISABLE.getCode());
						redisClusterMasterDao.updateById(redisClusterMaster);
						//------------------------------------------------------------------------------------------------------------
						List<RedisClusterSlave> clusterSlaves=this.getRedisClusterSlave(redisClusterMaster.getId());//获取主对应的从
												
						RedisClusterSlave redisClusterSlave=getHealthSlave(times, clusterSlaves,redisClusterMaster);//选出完好的从
						if(redisClusterSlave!=null){//选出从
							clusterSlaves.remove(redisClusterSlave);//剩下的从
							RedisClusterMaster newredisClusterMaster=createRedisClusterMaster(redisClusterSlave,redisClusterMaster);//从变为新的主
							for(RedisClusterSlave slave:clusterSlaves){//修改从的变化
								slave.setRedisMasterId(newredisClusterMaster.getId());
								redisClusterSlaveDao.updateById(redisClusterSlave);
							}
							//------------------------------------------------------------------------------------------------------------
							registerToZookeeper(redisClusterMaster, newredisClusterMaster);//注册新的zk redis关系数据
							changeRedisMasterSlave(clusterSlaves, newredisClusterMaster);//改变redis server主从关系
							saveSlaveToMasterLog(redisClusterMaster, newredisClusterMaster);
						}						
					}
				}
			}
		}
	}
	
	/**
	 * 主服务器挂了，记录新的日志
	 * @param oldRedisClusterMaster
	 * @param newredisClusterMaster
	 */
	private void saveSlaveToMasterLog(RedisClusterMaster oldRedisClusterMaster,RedisClusterMaster newredisClusterMaster){
		RedisClusterMonitorLog clusterMonitorLog=new RedisClusterMonitorLog();
		clusterMonitorLog.setCreateTime(new Date());
		clusterMonitorLog.setRedisServerHost(oldRedisClusterMaster.getRedisServerHost());
		clusterMonitorLog.setRedisServerPort(oldRedisClusterMaster.getRedisServerPort());
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("原主服务器已经DOWN,新主服务器:").append(newredisClusterMaster.getRedisServerHost()).append(":").append(newredisClusterMaster.getRedisServerPort()).append("已经起作用");
		clusterMonitorLog.setRemark(stringBuilder.toString());
		redisClusterMonitorLogDao.insert(clusterMonitorLog);
	}
	
	/**
	 * 注册新的数据
	 * @param oldClusterSlaves
	 * @param clusterSlaves
	 * @param redisClusterSlave
	 */
	private void registerToZookeeper(RedisClusterMaster oldRedisClusterMaster,RedisClusterMaster newredisClusterMaster){
		StringBuilder parentStringBuilder=new StringBuilder();
		parentStringBuilder.append(oldRedisClusterMaster.getRedisServerHost()).append(":").append(oldRedisClusterMaster.getRedisServerPort());
		String parentPath=parentStringBuilder.toString();
		
		StringBuilder childrenStringBuilder=new StringBuilder();
		childrenStringBuilder.append(newredisClusterMaster.getRedisServerHost()).append(":").append(newredisClusterMaster.getRedisServerPort());
		String path=childrenStringBuilder.toString();
		redisProxyZookeeper.deleteNode(parentPath, path);//删除代替主的从数据
		
		JSONObject jsonMasterObject=new JSONObject();
		jsonMasterObject.put("host", newredisClusterMaster.getRedisServerHost());
		jsonMasterObject.put("port", newredisClusterMaster.getRedisServerPort());
		redisProxyZookeeper.setNodeData(null, parentPath, jsonMasterObject.toJSONString());//注册新的数据
	}
	
	/**
	 * 改变redis 主从关系
	 * @param clusterSlaves
	 * @param newredisClusterMaster
	 */
	private void changeRedisMasterSlave(List<RedisClusterSlave> clusterSlaves,RedisClusterMaster newredisClusterMaster){
		Jedis jedisMaster=new Jedis(newredisClusterMaster.getRedisServerHost(), newredisClusterMaster.getRedisServerPort());
		jedisMaster.slaveofNoOne();//作为主
		jedisMaster.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
		for(RedisClusterSlave redisClusterSlave:clusterSlaves){//设redis 新主从
			Jedis jedisSlave=new Jedis(redisClusterSlave.getRedisServerHost(), redisClusterSlave.getRedisServerPort());
			jedisSlave.slaveof(newredisClusterMaster.getRedisServerHost(), newredisClusterMaster.getRedisServerPort());
			jedisSlave.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
			jedisSlave.close();
		}
		
		jedisMaster.close();
	}
	
	
	/**
	 * 删除原来从的位置
	 * 设置新的主
	 * @param redisClusterSlave
	 */
	private RedisClusterMaster createRedisClusterMaster(RedisClusterSlave redisClusterSlave,RedisClusterMaster oldRedisClusterMaster){
		redisClusterSlaveDao.deleteById(redisClusterSlave.getId());
		RedisClusterMaster redisClusterMaster=new RedisClusterMaster();
		redisClusterMaster.setCreateTime(new Date());
		redisClusterMaster.setOprateStatus(redisClusterSlave.getOprateStatus());
		redisClusterMaster.setRedisServerHost(redisClusterSlave.getRedisServerHost());
		redisClusterMaster.setRedisServerPort(redisClusterSlave.getRedisServerPort());
		redisClusterMaster.setRedisServerStatus(redisClusterSlave.getRedisServerStatus());
		redisClusterMaster.setRunnerStatus(redisClusterSlave.getRunnerStatus());
		redisClusterMaster.setPath(oldRedisClusterMaster.getPath());
		redisClusterMaster.setVersion(1);
		redisClusterMasterDao.insert(redisClusterMaster);
		return redisClusterMaster;
	}
	
	/**
	 * 检查主对应从服务器状态
	 * @param times
	 * @param masterId
	 */
	private RedisClusterSlave getHealthSlave(int times,List<RedisClusterSlave> clusterSlaves,RedisClusterMaster redisClusterMaster){

		if(clusterSlaves!=null){
			for(RedisClusterSlave redisClusterSlave:clusterSlaves){
				redisClusterSlave.setOprateStatus(OperateStatusEnums.DONE.getCode());
				int operatetimes= this.updateRunningRedisClusterSlave(redisClusterSlave);
				if(operatetimes>0){
					redisClusterSlave.setVersion(redisClusterSlave.getVersion()+1);
					boolean flag=getRedisStatusFlag(redisClusterSlave.getRedisServerHost(), redisClusterSlave.getRedisServerPort(), times);
					if(flag){//从好
						redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
						this.updateRunningRedisClusterSlave(redisClusterSlave);//一直执行完毕，下个执行
						return redisClusterSlave;
					}else{//从挂了
						redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
						redisClusterSlave.setRunnerStatus(TomcatStatusEnums.STOP.getCode());
						redisClusterSlave.setRedisServerStatus(StatusEnums.DISABLE.getCode());
						int count=redisClusterSlaveDao.updateById(redisClusterSlave);//一直执行完毕，下个执行
						if(count>0){
							StringBuilder parentStringBuilder=new StringBuilder();
							parentStringBuilder.append(redisClusterMaster.getRedisServerHost()).append(":").append(redisClusterMaster.getRedisServerPort());
							String parentPath=parentStringBuilder.toString();
							
							StringBuilder childrenStringBuilder=new StringBuilder();
							parentStringBuilder.append(redisClusterMaster.getRedisServerHost()).append(":").append(redisClusterMaster.getRedisServerPort());
							String path=childrenStringBuilder.toString();
							
							redisProxyZookeeper.deleteNode(parentPath, path);//删除从数据
							saveSlaveLog(redisClusterSlave);
						}
					}
				}
			}
		}
		return null;
		
	}
	
	
	/**
	 * 检查主对应从服务器状态
	 * @param times
	 * @param masterId
	 */
	private void checkSlaveStatus(int times,RedisClusterMaster redisClusterMaster){
		List<RedisClusterSlave> clusterSlaves=this.getRedisClusterSlave(redisClusterMaster.getId());
		if(clusterSlaves!=null){
			for(RedisClusterSlave redisClusterSlave:clusterSlaves){
				redisClusterSlave.setOprateStatus(OperateStatusEnums.DONE.getCode());
				int operatetimes= this.updateRunningRedisClusterSlave(redisClusterSlave);
				if(operatetimes>0){
					redisClusterSlave.setVersion(redisClusterSlave.getVersion()+1);
					boolean flag=getRedisStatusFlag(redisClusterSlave.getRedisServerHost(), redisClusterSlave.getRedisServerPort(), times);
					if(flag){//从好
						redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
						this.updateRunningRedisClusterSlave(redisClusterSlave);//一直执行完毕，下个执行
					}else{//从挂了
						redisClusterSlave.setOprateStatus(OperateStatusEnums.DOING.getCode());
						redisClusterSlave.setRunnerStatus(TomcatStatusEnums.STOP.getCode());
						redisClusterSlave.setRedisServerStatus(StatusEnums.DISABLE.getCode());
						int count=redisClusterSlaveDao.updateById(redisClusterSlave);//一直执行完毕，下个执行
						if(count>0){
							StringBuilder parentStringBuilder=new StringBuilder();
							parentStringBuilder.append(redisClusterMaster.getRedisServerHost()).append(":").append(redisClusterMaster.getRedisServerPort());
							String parentPath=parentStringBuilder.toString();
							
							StringBuilder childrenStringBuilder=new StringBuilder();
							parentStringBuilder.append(redisClusterMaster.getRedisServerHost()).append(":").append(redisClusterMaster.getRedisServerPort());
							String path=childrenStringBuilder.toString();
							
							redisProxyZookeeper.deleteNode(parentPath, path);//删除从数据
							saveSlaveLog(redisClusterSlave);
						}
						
					}
				}
				
			}
		}
	}
	
	/**
	 * 主服务器挂了，记录新的日志
	 * @param oldRedisClusterMaster
	 * @param newredisClusterMaster
	 */
	private void saveSlaveLog(RedisClusterSlave redisClusterSlave){
		RedisClusterMonitorLog clusterMonitorLog=new RedisClusterMonitorLog();
		clusterMonitorLog.setCreateTime(new Date());
		clusterMonitorLog.setRedisServerHost(redisClusterSlave.getRedisServerHost());
		clusterMonitorLog.setRedisServerPort(redisClusterSlave.getRedisServerPort());
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("从Redis主服务器:").append(redisClusterSlave.getRedisServerHost()).append(":").append(redisClusterSlave.getRedisServerPort()).append("已经DOWN");
		clusterMonitorLog.setRemark(stringBuilder.toString());
		redisClusterMonitorLogDao.insert(clusterMonitorLog);
	}
	
	 /**
     * redis是否连通
     *
     * @param jedisPool
     * @param tryTimes
     * @return
     */
    private boolean getRedisStatusFlag(String host,int port, int tryTimes) {
        boolean isRedisCrash = true;
        //循环10次获取Redis主是否挂掉
        try {
            Jedis jedis=new Jedis(host,port);
            jedis.info();
            jedis.close();
        } catch (Exception e) {
            if (tryTimes <=5) {
                tryTimes = tryTimes + 1;
                return getRedisStatusFlag(host,port, tryTimes);
            } else {
                return false;
            }
        }
        return isRedisCrash;
    }
	
	/**
	 * 获取主服务列表
	 * @return
	 */
	private List<RedisClusterMaster> getRunningRedisClusterMasters(){
		List<RedisClusterMaster> redisClusterMasters=redisClusterMasterDao.getListByStatus();
		if(redisClusterMasters!=null&&redisClusterMasters.size()>0){
			return redisClusterMasters;
		}
		return null;
	}
	
	/**
	 * 修改主服务 
	 * @param redisClusterMaster
	 * @return
	 */
	private Integer updateRunningRedisClusterMaster(RedisClusterMaster redisClusterMaster){
		return redisClusterMasterDao.updateVersionByIdAndVersion(redisClusterMaster);
	}
	
	/**
	 * 获取从服务器列表，根据主服务器主键
	 * @param masterId
	 * @return
	 */
	private List<RedisClusterSlave> getRedisClusterSlave(Integer masterId){
		return redisClusterSlaveDao.getListByMasterId(masterId);
	}
	
	/**
	 * 修改从服务 
	 * @param redisClusterMaster
	 * @return
	 */
	private Integer updateRunningRedisClusterSlave(RedisClusterSlave redisClusterSlave){
		return redisClusterSlaveDao.updateVersionByIdAndVersion(redisClusterSlave);
	}
	
}
