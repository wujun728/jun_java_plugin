package com.opensource.nredis.proxy.monitor.api;


import java.util.ArrayList;
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
public class RedisClusterMasterController {
	
	@Autowired
	private IRedisClusterMasterService redisClusterMasterService;
	
	@Autowired
	private NRedisProxyZookeeper redisProxyZookeeper;
	@Autowired
	private IRedisClusterSlaveService redisClusterSlaveService;
	@Autowired
	private IRedisService redisService ;
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	private static final Integer EXPIRETIME=24*60*60; //过期时间
	
	private static final Integer TOTAL=10;//过期时间
	/**
	 * 新增
	 * @param redisClusterMaster
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redisClusterMaster", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject redisClusterMaster(RedisClusterMaster redisClusterMaster) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterMasterService.create(redisClusterMaster);
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
	@RequestMapping(value = "/editRedisClusterMaster", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editRedisClusterMaster(RedisClusterMaster redisClusterMaster) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		
		RedisClusterSlave redisClusterSlave=new RedisClusterSlave();
		redisClusterSlave.setRedisMasterHost(redisClusterMaster.getRedisServerHost());
		redisClusterSlave.setRedisServerPort(redisClusterMaster.getRedisServerPort());
		List<RedisClusterSlave> redisClusterSlaves=redisClusterSlaveService.queryEntityList(redisClusterSlave);
		if(redisClusterSlaves!=null&&redisClusterSlaves.size()>0){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage("机器已经存在于从服务器中");
			return responseObject;
		}
		
		RedisClusterMaster oldRedisClusterMaster=redisClusterMasterService.getEntityById(redisClusterMaster.getId());
		if(!redisClusterMaster.getRedisServerHost().equals(oldRedisClusterMaster.getRedisServerHost())||redisClusterMaster.getRedisServerPort()!=oldRedisClusterMaster.getRedisServerPort()){
			changeZkMasterData(redisClusterMaster, oldRedisClusterMaster);
			changeRedisMasterData(redisClusterMaster,oldRedisClusterMaster.getId());
			redisClusterMaster.setOprateStatus(oldRedisClusterMaster.getOprateStatus());
			redisClusterMaster.setPath(oldRedisClusterMaster.getPath());
			redisClusterMaster.setRedisServerStatus(oldRedisClusterMaster.getRedisServerStatus());
			redisClusterMaster.setRunnerStatus(oldRedisClusterMaster.getRunnerStatus());
			redisClusterMaster.setVersion(oldRedisClusterMaster.getVersion());
			redisClusterMaster.setCreateTime(oldRedisClusterMaster.getCreateTime());
			redisClusterMasterService.modifyEntityById(redisClusterMaster);
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	/**
	 * 更改zkMaster信息
	 * @param newRedisClusterMaster
	 * @param oldRedisClusterMaster
	 */
	private void changeZkMasterData(RedisClusterMaster newRedisClusterMaster,RedisClusterMaster oldRedisClusterMaster){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("host", newRedisClusterMaster.getRedisServerHost());
		jsonObject.put("port", newRedisClusterMaster.getRedisServerPort());
		redisProxyZookeeper.getZkClient().writeData(oldRedisClusterMaster.getPath(), jsonObject.toJSONString());//改变值
	}
	
	/**
	 * 更改redis server master/slave 对应关系
	 * @param newRedisClusterMaster
	 * @throws Exception
	 */
	private void changeRedisMasterData(RedisClusterMaster newRedisClusterMaster,Integer oldMasterId) throws Exception{
		Jedis jedisMaster=new Jedis(newRedisClusterMaster.getRedisServerHost(), newRedisClusterMaster.getRedisServerPort());
		jedisMaster.slaveofNoOne();//作为主
		jedisMaster.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
		RedisClusterSlave redisClusterSlave=new RedisClusterSlave();
		redisClusterSlave.setRedisMasterId(oldMasterId);
		List<RedisClusterSlave> redisClusterSlaves=redisClusterSlaveService.queryEntityList(redisClusterSlave);
		for(RedisClusterSlave redisSlave:redisClusterSlaves){//设redis 新主从
			Jedis jedisSlave=new Jedis(redisSlave.getRedisServerHost(), redisSlave.getRedisServerPort());
			jedisSlave.slaveof(newRedisClusterMaster.getRedisServerHost(), newRedisClusterMaster.getRedisServerPort());
			jedisSlave.eval("return redis.call('CONFIG','REWRITE')");//更新配置文件
			jedisSlave.close();
		}
		
		jedisMaster.close();
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchRedisClusterMaster", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterMaster redisClusterMaster= redisClusterMasterService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(redisClusterMaster);
		return responseObject;
	}
	
	/**
	 * 查询图形化数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/{id}/searchRedisMasterImageData", method = RequestMethod.GET)
	@ResponseBody
	public RedisEChartsObject searchRedisMasterImageData(@PathVariable Integer id) throws Exception{
		RedisEChartsObject responseObject=new RedisEChartsObject();
		RedisClusterMaster redisClusterMaster= redisClusterMasterService.getEntityById(id);
		String host=redisClusterMaster.getRedisServerHost();
		int port=redisClusterMaster.getRedisServerPort();
		if(redisClusterMaster!=null&&!StringUtil.isEmpty(host)){
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
				RedisConfigInfo redisConfigInfo=redisService.getRedisConfigInfo(redisClusterMaster.getRedisServerHost(), redisClusterMaster.getRedisServerPort());
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
	
	/**
	 * 查询redis 配置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchRedisInfoElement", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchRedisInfoElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterMaster redisClusterMaster= redisClusterMasterService.getEntityById(id);
		Jedis jedis=new Jedis(redisClusterMaster.getRedisServerHost(), redisClusterMaster.getRedisServerPort());
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
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeRedisClusterMaster", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeRedisClusterMaster(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterMasterService.deleteEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询列表
	 * @param menuId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchRedisClusterMasters", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchRedisClusterMasters(RedisClusterMaster redisClusterMaster,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<RedisClusterMaster> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=redisClusterMasterService.queryEntityPageList(pageAttribute, redisClusterMaster, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		List<RedisClusterMaster> redisClusterMasters=pageList.getDatas();
		for(RedisClusterMaster clusterMaster:redisClusterMasters){
			clusterMaster.setRunnerStatusString(TomcatStatusEnums.getMessage(clusterMaster.getRunnerStatus()));
			clusterMaster.setRedisServerStatusString(StatusEnums.getMessage(clusterMaster.getRedisServerStatus()));
			//application.setCreateTimeString(DateBase.formatDate(application.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
			clusterMaster.setCreateTimeString(DateBase.formatDate(clusterMaster.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
		}		
		responseObject.setRows(pageList.getDatas());
		return responseObject;
	}
	
}
