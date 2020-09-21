package com.opensource.nredis.proxy.monitor.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMonitorLog;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterMonitorLogService;
/**
* controller
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
@Controller
public class RedisClusterMonitorLogController {
	
	@Autowired
	private IRedisClusterMonitorLogService redisClusterMonitorLogService;
	
	/**
	 * 新增
	 * @param redisClusterMonitorLog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redisClusterMonitorLog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject redisClusterMonitorLog(RedisClusterMonitorLog redisClusterMonitorLog) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterMonitorLogService.create(redisClusterMonitorLog);
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
	@RequestMapping(value = "/editRedisClusterMonitorLog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editRedisClusterMonitorLog(RedisClusterMonitorLog redisClusterMonitorLog) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterMonitorLogService.modifyEntityById(redisClusterMonitorLog);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchRedisClusterMonitorLog", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		RedisClusterMonitorLog redisClusterMonitorLog= redisClusterMonitorLogService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(redisClusterMonitorLog);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeRedisClusterMonitorLog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeRedisClusterMonitorLog(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		redisClusterMonitorLogService.deleteEntityById(id);
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
	@RequestMapping(value = "/searchRedisClusterMonitorLogs", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchRedisClusterMonitorLogs(RedisClusterMonitorLog redisClusterMonitorLog,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<RedisClusterMonitorLog> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=redisClusterMonitorLogService.queryEntityPageList(pageAttribute, redisClusterMonitorLog, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		List<RedisClusterMonitorLog> clusterMonitorLogs=pageList.getDatas();
		for(RedisClusterMonitorLog clusterMonitorLog:clusterMonitorLogs){
			clusterMonitorLog.setCreateTimeString(DateBase.formatDate(clusterMonitorLog.getCreateTime(), DateBase.DATE_PATTERN_DATETIME));
		}
		
		responseObject.setRows(clusterMonitorLogs);
		return responseObject;
	}
	
}
