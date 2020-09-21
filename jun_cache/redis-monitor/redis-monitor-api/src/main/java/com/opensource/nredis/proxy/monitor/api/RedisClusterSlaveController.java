package com.opensource.nredis.proxy.monitor.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.enums.OperateStatusEnums;
import com.opensource.nredis.proxy.monitor.enums.StatusEnums;
import com.opensource.nredis.proxy.monitor.enums.TomcatStatusEnums;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMaster;
import com.opensource.nredis.proxy.monitor.model.RedisClusterSlave;
import com.opensource.nredis.proxy.monitor.model.RedisConfigInfo;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.NormalAreaStyle;
import com.opensource.nredis.proxy.monitor.platform.RedisEChartsObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterMasterService;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterSlaveService;
import com.opensource.nredis.proxy.monitor.service.IRedisService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
import com.opensource.nredis.proxy.monitor.zookeeper.NRedisProxyZookeeper;
/**
* controller
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
@Controller
public class RedisClusterSlaveController {
	
	@Autowired
	private IRedisClusterSlaveService redisClusterSlaveService;
	
	@Autowired
	private IRedisClusterMasterService redisClusterMasterService;
	
	@Autowired
	private NRedisProxyZookeeper redisProxyZookeeper;
	
	@Autowired
	private IRedisService redisService ;
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	private static final Integer EXPIRETIME=24*60*60; //过期时间
	
	private static final Integer TOTAL=10;//过期时间
	/**
	 * 新增
	 * @param redisClusterSlave
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redisClusterSlave", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject redisClusterSlave(RedisClusterSlave redisClusterSlave) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterSlaveService.create(redisClusterSlave);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 修改
	 * @param pageElement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editRedisClusterSlave", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editRedisClusterSlave(RedisClusterSlave redisClusterSlave) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterMaster clusterMaster=new RedisClusterMaster();
		clusterMaster.setRedisServerHost(redisClusterSlave.getRedisMasterHost());
		clusterMaster.setRedisServerPort(redisClusterSlave.getRedisServerPort());
		List<RedisClusterMaster> clusterMasters=redisClusterMasterService.queryEntityList(clusterMaster);
		if(clusterMasters!=null&&clusterMasters.size()>0){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage("机器已经存在于主服务器中");
			return responseObject;
		}
		
		RedisClusterSlave oldRedisClusterSlave=redisClusterSlaveService.getEntityById(redisClusterSlave.getId());
		boolean weightFlag=false,serverFlag=false;
		if(!oldRedisClusterSlave.getRedisServerHost().equals(redisClusterSlave.getRedisServerHost())||oldRedisClusterSlave.getRedisMasterPort()!=redisClusterSlave.getRedisServerPort()||oldRedisClusterSlave.getWeight()!=redisClusterSlave.getWeight()){
			if(oldRedisClusterSlave.getWeight()!=redisClusterSlave.getWeight()){
				weightFlag=true;//更改
			}
			if(!oldRedisClusterSlave.getRedisServerHost().equals(redisClusterSlave.getRedisServerHost())||oldRedisClusterSlave.getRedisMasterPort()!=redisClusterSlave.getRedisServerPort()){
				serverFlag=true;//更改
			}
			redisClusterSlave.setCreateTime(oldRedisClusterSlave.getCreateTime());
			redisClusterSlave.setOprateStatus(oldRedisClusterSlave.getOprateStatus());
			redisClusterSlave.setPath(oldRedisClusterSlave.getPath());
			redisClusterSlave.setRedisMasterId(oldRedisClusterSlave.getRedisMasterId());
			redisClusterSlave.setRedisServerStatus(oldRedisClusterSlave.getRedisServerStatus());
			redisClusterSlave.setRunnerStatus(oldRedisClusterSlave.getRunnerStatus());
			redisClusterSlave.setVersion(oldRedisClusterSlave.getVersion());
			redisClusterSlaveService.modifyEntityById(redisClusterSlave);//更改数据
			
			changeSlaveZkAndRedis(weightFlag, serverFlag,redisClusterSlave,oldRedisClusterSlave);//更改zk 与redis
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 更改zk 与redis
	 * @param weightFlag
	 * @param serverFlag
	 * @param redisClusterSlave
	 */
	private void changeSlaveZkAndRedis(boolean weightFlag,boolean serverFlag,RedisClusterSlave redisClusterSlave,RedisClusterSlave oldRedisClusterSlave) throws Exception{
		if(serverFlag){//更改机器
			Jedis jedis= new Jedis(redisClusterSlave.getRedisServerHost(),redisClusterSlave.getRedisServerPort());
			RedisClusterMaster redisClusterMaster=redisClusterMasterService.getEntityById(redisClusterSlave.getRedisMasterId());
			jedis.slaveof(redisClusterMaster.getRedisServerHost(), redisClusterMaster.getRedisServerPort());
			jedis.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
			jedis.close();
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("host", redisClusterSlave.getRedisServerHost());
		jsonObject.put("port", redisClusterSlave.getRedisServerPort());
		jsonObject.put("weight", redisClusterSlave.getWeight());
		redisProxyZookeeper.getZkClient().writeData(redisClusterSlave.getPath(), jsonObject.toJSONString());//改变值
		
		if(oldRedisClusterSlave!=null&&StringUtil.isNotEmpty(oldRedisClusterSlave.getRedisServerHost())){//旧的从就不在依赖于主
			Jedis jedis= new Jedis(oldRedisClusterSlave.getRedisServerHost(),oldRedisClusterSlave.getRedisServerPort());
			jedis.slaveofNoOne();
			jedis.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
			jedis.close();
		}
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchRedisClusterSlave", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterSlave redisClusterSlave= redisClusterSlaveService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(redisClusterSlave);
		return responseObject;
	}
	
	/**
	 * 查询redis 配置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchSlaveRedisInfoElement", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchRedisInfoElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterSlave redisClusterSlave= redisClusterSlaveService.getEntityById(id);
		Jedis jedis=new Jedis(redisClusterSlave.getRedisServerHost(), redisClusterSlave.getRedisServerPort());
		String result=jedis.info();
		List<JSONObject> jsonObjects=new ArrayList<JSONObject>();
		if(StringUtil.isNotEmpty(result)){
			String group="";
			String[] datas=result.split("\r\n");
			for(String data:datas){
				JSONObject jsonObject=new JSONObject();
				if(data.contains("#")){
					group=data;
				}else{
					jsonObject.put("text", data);
					jsonObject.put("group", group);
					jsonObjects.add(jsonObject);
				}
				
			}	
		}
		jedis.close();
		responseObject.setData(jsonObjects);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	/**
	 * 设为主
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setRedisClusterSlaveToMaster", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject setRedisClusterSlaveToMaster(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterSlave redisClusterSlave=redisClusterSlaveService.getEntityById(id);//获取从
		if(redisClusterSlave.getRedisServerStatus()==StatusEnums.ENABLE.getCode()&&redisClusterSlave.getRunnerStatus()==TomcatStatusEnums.RUNNING.getCode()){
			redisClusterSlaveService.deleteEntityById(id);
			List<RedisClusterSlave> otherRedisClusterSlaves=this.getOtherRedisClusterSlaves(redisClusterSlave.getRedisMasterId(), id);
			RedisClusterMaster oldRedisClusterMaster=redisClusterMasterService.getEntityById(redisClusterSlave.getRedisMasterId());

			RedisClusterMaster redisClusterMaster=new RedisClusterMaster();
			redisClusterMaster.setCreateTime(new Date());
			redisClusterMaster.setOprateStatus(redisClusterSlave.getOprateStatus());
			redisClusterMaster.setRedisServerHost(redisClusterSlave.getRedisServerHost());
			redisClusterMaster.setRedisServerPort(redisClusterSlave.getRedisServerPort());
			redisClusterMaster.setRedisServerStatus(redisClusterSlave.getRedisServerStatus());
			redisClusterMaster.setRunnerStatus(redisClusterSlave.getRunnerStatus());
			redisClusterMaster.setVersion(1);
			redisClusterMaster.setPath(oldRedisClusterMaster.getPath());
			redisClusterMasterService.create(redisClusterMaster);
			
			for(RedisClusterSlave slave:otherRedisClusterSlaves){//修改从的变化
				slave.setRedisMasterId(redisClusterMaster.getId());
				redisClusterSlaveService.modifyEntityById(redisClusterSlave);
			}
			//------------下面修改老的主-------------------------
			oldRedisClusterMaster.setOprateStatus(OperateStatusEnums.DONE.getCode());
			oldRedisClusterMaster.setRunnerStatus(TomcatStatusEnums.STOP.getCode());
			oldRedisClusterMaster.setRedisServerStatus(StatusEnums.DISABLE.getCode());
			redisClusterMasterService.modifyEntityById(oldRedisClusterMaster);
			//---------------下面是注册新的zk master/slave关系--------------------------------------
			registerToZookeeper(oldRedisClusterMaster, redisClusterMaster);
			//-----------------下面是修改redis 对应关系-----------------------------------------------------------------
			changeRedisMasterSlave(otherRedisClusterSlaves, redisClusterMaster);
		}else{
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage("服务器不可用");
			return responseObject;
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
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
	 * 获取其它对应从
	 * @param masterId 主服务主键
	 * @param id 从服务id
	 * @return
	 * @throws Exception
	 */
	private List<RedisClusterSlave> getOtherRedisClusterSlaves(int masterId, int id) throws Exception{
		List<RedisClusterSlave> result=new ArrayList<RedisClusterSlave>();
		RedisClusterSlave redisClusterSlave=new RedisClusterSlave();
		redisClusterSlave.setRedisMasterId(masterId);
		List<RedisClusterSlave> redisClusterSlaves=redisClusterSlaveService.queryEntityList(redisClusterSlave);
		
		for(RedisClusterSlave slave:redisClusterSlaves){
			if(slave.getId()!=id){
				result.add(slave);
			}
		}
		return result;
		
	}
	
	
	/**
	 * 查询列表
	 * @param menuId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchRedisClusterSlaves", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchRedisClusterSlaves(RedisClusterSlave redisClusterSlave,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<RedisClusterSlave> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=redisClusterSlaveService.queryEntityPageList(pageAttribute, redisClusterSlave, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		List<RedisClusterSlave> redisClusterSlaves=pageList.getDatas();
		for(RedisClusterSlave slave:redisClusterSlaves){
			slave.setRunnerStatusString(TomcatStatusEnums.getMessage(slave.getRunnerStatus()));
			slave.setRedisServerStatusString(StatusEnums.getMessage(slave.getRedisServerStatus()));
			slave.setCreateTimeString(DateBase.formatDate(slave.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
			RedisClusterMaster redisClusterMaster=redisClusterMasterService.getEntityById(slave.getRedisMasterId());
			slave.setRedisMasterHost(redisClusterMaster.getRedisServerHost());
			slave.setRedisMasterPort(redisClusterMaster.getRedisServerPort());
		}
		responseObject.setRows(redisClusterSlaves);
		return responseObject;
	}
	
	
	/**
	 * 查询图形化数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/{id}/searchRedisSlaveImageData", method = RequestMethod.GET)
	@ResponseBody
	public RedisEChartsObject searchRedisSlaveImageData(@PathVariable Integer id) throws Exception{
		RedisEChartsObject responseObject=new RedisEChartsObject();
		RedisClusterSlave redisClusterSlave= redisClusterSlaveService.getEntityById(id);
		String host=redisClusterSlave.getRedisServerHost();
		int port=redisClusterSlave.getRedisServerPort();
		if(redisClusterSlave!=null&&!StringUtil.isEmpty(host)){
			ShardedJedis shardedJedis=shardedJedisPool.getResource();
			try{
				List<String> times=getResultFromRedis(host, port,"_times",String.class,shardedJedis);//时间
				List<Integer> clientConnections=getResultFromRedis(host, port,"_connection",Integer.class,shardedJedis);//连接数
				List<Integer> totalCommands=getResultFromRedis(host, port,"_command",Integer.class,shardedJedis);//处理命令数
				List<Double> keyHits=getResultFromRedis(host, port,"_keyHit",Double.class,shardedJedis);//命中率
				List<Double> usedSystemCPUs=getResultFromRedis(host, port,"_usedSystemCPU",Double.class,shardedJedis);
				List<Double> usedHumanCPUs=getResultFromRedis(host, port,"_usedHumanCPU",Double.class,shardedJedis);
				List<Double> sysMemorys=getResultFromRedis(host, port,"_sysMemory",Double.class,shardedJedis);
				List<Double> usedMemorys=getResultFromRedis(host, port,"_usedMemory",Double.class,shardedJedis);
				RedisConfigInfo redisConfigInfo=redisService.getRedisConfigInfo(host, port);
				//--------------------------------------获取配置--------------------------------------------------------------------------------
				times.add(redisConfigInfo.getTimestamp());
				clientConnections.add(redisConfigInfo.getClientConnection());
				totalCommands.add(redisConfigInfo.getTotalCommands());
				usedMemorys.add(redisConfigInfo.getUsedMemory());
				sysMemorys.add(redisConfigInfo.getSysMemory());
				usedHumanCPUs.add(redisConfigInfo.getUsedHumanCPU());
				usedSystemCPUs.add(redisConfigInfo.getUsedSystemCPU());

				Double total=redisConfigInfo.getKeyHits()+redisConfigInfo.getKeyMiss();
				if(total==0){
					keyHits.add(0D);
				}else{
					Double ration=(Double)redisConfigInfo.getKeyHits()/(redisConfigInfo.getKeyHits()+redisConfigInfo.getKeyMiss());
					keyHits.add(ration*100);
				}
				
				//------------------------------------------存储数据--------------------------------------------------------------
				saveResultToRedis(host, port, "_times", shardedJedis, JSONObject.toJSONString(times));
				saveResultToRedis(host, port, "_connection", shardedJedis, JSONObject.toJSONString(clientConnections));
				saveResultToRedis(host, port, "_command", shardedJedis, JSONObject.toJSONString(totalCommands));
				saveResultToRedis(host, port, "_keyHit", shardedJedis, JSONObject.toJSONString(keyHits));
				saveResultToRedis(host, port, "_usedSystemCPU", shardedJedis, JSONObject.toJSONString(usedSystemCPUs));
				saveResultToRedis(host, port, "_usedHumanCPU", shardedJedis, JSONObject.toJSONString(usedHumanCPUs));
				saveResultToRedis(host, port, "_sysMemory", shardedJedis, JSONObject.toJSONString(sysMemorys));
				saveResultToRedis(host, port, "_usedMemory", shardedJedis, JSONObject.toJSONString(usedMemorys));
				//---------------------------------------更新图形化数据配置------------------------------------------------------------------
				StringBuilder stringBuilder=new StringBuilder();
				stringBuilder.append("服务器:").append(host).append(":").append(port);
				Map<String, List<?>> results=new HashMap<String, List<?>>();
				results.put("connected_clients", clientConnections);
				Option clientConnectionOption=getOption(stringBuilder.toString(), "客户端连接数", times, results);
				
				results=new HashMap<String, List<?>>();
				results.put("total_commands_processed", totalCommands);
				Option totalCommandOption=getOption(stringBuilder.toString(),"Redis处理命令数",times,results);
				
				results=new HashMap<String, List<?>>();
				results.put("keyHitPercents", keyHits);
				Option keyHitOption=getOption(stringBuilder.toString(),"Redis命中率",times,results);
				
				results=new HashMap<String, List<?>>();
				results.put("used_cpu_sys", usedSystemCPUs);
				results.put("used_cpu_user", usedHumanCPUs);
				Option usedCPUOption=getOption(stringBuilder.toString(),"Redis CPU",times,results);
				
				results=new HashMap<String, List<?>>();
				results.put("used_memory_peak_human", sysMemorys);
				results.put("used_memory_human", usedMemorys);
				Option usedMemoryption=getOption(stringBuilder.toString(),"Redis 内存",times,results);
				responseObject.setClientConnectionOption(clientConnectionOption);
				responseObject.setKeyHitOption(keyHitOption);
				responseObject.setTotalCommandOption(totalCommandOption);
				responseObject.setTotalCommandOption(totalCommandOption);
				responseObject.setUsedCPUOption(usedCPUOption);
				responseObject.setUsedMemoryption(usedMemoryption);
				
			}finally{
				shardedJedisPool.returnResource(shardedJedis);
			}
			
		}
				
		return responseObject;
	}
	
	/**
	 * 存储在redis 中
	 * @param host
	 * @param port
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getResultFromRedis(String host,Integer port,String secondkey,Class<?> cls,ShardedJedis shardedJedis){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("host").append("_").append(port).append(secondkey);
		String key=stringBuilder.toString();
		if(shardedJedis.exists(key)){
				String result=shardedJedis.get(key);
				List times=JSONObject.parseArray(result, cls);
				if(times!=null&&times.size()>TOTAL){
					times.subList(0, times.size()-TOTAL).clear();
				}
				return times;
		}else{
				return new ArrayList();
		}
	}
	
	/**
	 * 存储于redis
	 * @param host
	 * @param port
	 * @param secondkey
	 * @param shardedJedis
	 * @param result
	 */
	private void saveResultToRedis(String host,Integer port,String secondkey,ShardedJedis shardedJedis,String result){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("host").append("_").append(port).append(secondkey);
		String key=stringBuilder.toString();
		shardedJedis.set(key, result);
		shardedJedis.expire(key, EXPIRETIME);
	}
	
	/**
	 * 获取各种图形配置
	 * @param title
	 * @param subtitle
	 * @param legendName
	 * @param times
	 * @param datas
	 * @return
	 */
	private Option getOption(String subTitle, String title,List<String> times,Map<String, List<?>> mapDatas){
		Option option = getOption(title,subTitle);
		
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(times);
		option.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		option.yAxis(categoryAxis);
		@SuppressWarnings("rawtypes")
		List<Series> series =new ArrayList<Series>();
		StringBuilder stringBuilder=new StringBuilder();

		for(String key:mapDatas.keySet()){
			Line line=new Line();
			line.setName(key);
			line.setType(SeriesType.line);
			NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
			nomalAreaStyle.setNormal("{}");
			line.setAreaStyle(nomalAreaStyle);
			line.setData(mapDatas.get(key));
			series.add(line);
			stringBuilder.append(key).append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		option.tooltip(Trigger.axis).legend().data(stringBuilder.toString());
		option.setSeries(series);
		return option;
	}
	
	
	/**
	 * 获取图形化配置
	 * @param title
	 * @param subTitle
	 * @return
	 */
	private Option getOption(String title,String subTitle){
		Option option = new Option();
		Grid grid=new Grid();
		grid.setLeft("3%");
		grid.setRight("4%");
		grid.setBottom("3%");
		grid.setContainLabel(true);
		if(StringUtil.isEmpty(title)){
			option.tooltip(Trigger.axis).grid(grid);
		}else{
			if(StringUtil.isNotEmpty(subTitle)){
				option.title(title,subTitle).tooltip(Trigger.axis).grid(grid);
			}else{
				option.title(title).tooltip(Trigger.axis).grid(grid);
			}
		}
		
		return option;
	}
	
	
}
