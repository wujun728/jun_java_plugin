package com.jun.plugin.common.base.interfaces;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
//import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
//import com.jfinal.plugin.activerecord.Page;
//import com.jfinal.plugin.activerecord.Record;
//import com.jfinal.plugin.druid.DruidPlugin;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.db.record.Db;
import com.jun.plugin.db.record.Page;
import com.jun.plugin.db.record.Record;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class AbstractExecutor<T,P> {
	protected static final int SUCCESS = 0;
	protected static final int RESPONSE_ERROR = 1;
	protected static final int RESPONSE_NULL = 100;
	protected static final int ERROR = -1;
	protected static final String MASTER = "master";
	protected boolean debug = false;
	protected HttpServletRequest request;

	protected HttpServletResponse response;

	public void init(HttpServletRequest request,HttpServletResponse response){//普通方法
		this.request = request;
		this.response = response;
		System.out.println("init 方法体的初始化方法 111  ");
	}

	public abstract T execute(P params) throws Exception;//抽象方法，没有方法体，有abstract关键字做修饰

//	public abstract T rollback(P params);

	Context context;
	
//	public AbstractExecutor(Context context) {
//		this.context = context;
//	}

	public void initDb() {
		initDb(MASTER, SpringUtil.getProperty("spring.datasource.url"),
				SpringUtil.getProperty("spring.datasource.username"),
				SpringUtil.getProperty("spring.datasource.password"));
	}
	public void initDb(String appNo, String url, String username, String password) {
		Boolean isExtsis = false;
		try {
			Db.init(url,username, password);
//			DruidPlugin dp = new DruidPlugin(url, username, password);
//			ActiveRecordPlugin arp = new ActiveRecordPlugin(appNo, dp);
//			arp.setDevMode(true);
//			arp.setShowSql(true);
//			dp.start();
//			arp.start();
			log.warn("Config have bean created by configName: {}",appNo);
			//Db.use(appNo);
		} catch (IllegalArgumentException e) {
			isExtsis = true;
			log.info(e.getMessage());
		}
		if( !isExtsis ){

		}
	}

	public Page<Map> getPageMaps(Page<Record> pages){
		List<Record> records = pages.getList();
		Page<Map> page = new Page();
		List<Map> datas = Lists.newArrayList();
		if(records.size()>0){
			for(Record record : records){
				datas.add(record.getColumns());
			}
		}
		page.setList(datas);
		page.setPageNumber(pages.getPageNumber());
		page.setPageSize(pages.getPageSize());
		page.setTotalPage(pages.getTotalPage());
		page.setTotalRow(pages.getTotalRow());
		return page;
	}

	// protected FlowType flowType;
//	public Map<String,Object> collectParams(Map<String,Object> params) {
//		//序列化时过滤掉request和response
//		Map<String, Object> collect = MapUtil.filter(params, map -> (!(map.getValue() instanceof HttpServletRequest) && !(map.getValue() instanceof HttpServletResponse)));
//		return collect;
//	}
	 
	protected Map<String,Object> parameters; // 创建日期
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	// 获取组件参数，经过El解析引擎得到最终的参数值
	public String getParaStr(String paramName) {
		return getPara(paramName);
	}

	/**
	 * 参数值可以为空
	 * @param paramName
	 * @return
	 */
	public String getPara(String paramName) {
		String val = MapUtil.getStr(parameters, paramName);
		printMsg(paramName,val);
		return (val == null) ? "" : val;
	}

	/**
	 * 参数值不能为空
	 * @param parameters
	 * @param paramName
	 * @return
	 */
	public String getPara(Map parameters,String paramName) {
		String val = MapUtil.getStr(parameters, paramName);
		if(val == null){
			throw new BusinessException("参数["+paramName + "]不能为空！");
		}
		printMsg(paramName,val);
		return val;
	}


	/**
	 * 参数值不能为空
	 * @param parameters
	 * @param paramName
	 * @return
	 * @throws BusinessException
	 */
	public int getParaInt(Map parameters,String paramName) throws BusinessException {
		String val = MapUtil.getStr(parameters, paramName);
		if(val == null){
			throw new BusinessException("参数["+paramName + "]不能为空！");
		}else{
			try {
				printMsg(paramName,val);
				return Integer.valueOf(val);
			} catch (NumberFormatException e) {
				throw new BusinessException("参数["+paramName + "]"+"不能为非数字！");
			}
		}
	}

	/**
	 * 参数值可以为空
	 * @param paramName
	 * @return
	 * @throws BusinessException
	 */
	public int getParaInt(String paramName) throws BusinessException {
		Integer val = MapUtil.getInt(parameters, paramName);
		if(val == null){
			throw new BusinessException("参数["+paramName + "]不能为空！");
		}
		printMsg(paramName,val);
		return val;
	}

	protected Object getParaObject(String paramName) {
		Object val = MapUtil.get(parameters, paramName, Object.class);
		printMsg(paramName,val);
		return (val == null) ? "" : val;
	}

	protected String getId(String val) {
		printMsg(val);
		if("{_objectId}".equalsIgnoreCase(val)){
			return IdUtil.objectId();
		}
		else if("{_simpleUUID}".equalsIgnoreCase(val)){
			return IdUtil.simpleUUID();
		}
		else if("{_snowflakeId}".equalsIgnoreCase(val)){
			return IdUtil.getSnowflakeNextIdStr();
		}
		else if("{_nanoId}".equalsIgnoreCase(val)){
			return IdUtil.nanoId();
		}else {
			return val;
		}
	}

	/** 获取组件参数，经过El解析引擎得到最终的参数值(防止参数值出现“，”分隔符) kht **/
	protected Object[] getParas(String paramName) {
		Object[] results = null;
		Object obj = getParaObject(paramName);
		if (obj == null || "".equals(obj.toString()) || obj.toString().trim().length() == 0) {
			return new Object[0];
		} else {
			String para = obj.toString().trim();
			String reg = "\\w*\\$\\{([^\\}^\\{]*[,]+[^\\}^\\{]*)\\}\\w*";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(para);
			List<String> list = new ArrayList<String>();
			while (m.find()) {
				list.add(m.group(0));
			}
			int num = 0;
			String repArg = para.replaceAll(reg, "&_#");
			String[] paras = repArg.split(",", -1);
			results = new Object[paras.length];
			for (int i = 0; i < paras.length; i++) {
				if ("&_#".equals(paras[i])) {
					paras[i] = list.get(num++);
				}
				Object result = getParaObject(paramName);
				results[i] = result == null ? "" : result;
			}
		}
		return results;
	}

	/**
	 * 组件执行成功公共处理
	 */
	protected void compSuccReturn() {
		String sData = "组件" + this.getClass().getName() + "执行成功";
		log.info(sData);
	}

	/**
	 * 组件执行成功公共处理
	 */
	protected void compSuccReturnWithMsg(String describe) {
		String sData = "组件" + this.getClass().getName() + "执行成功,具体信息：" + describe;
		log.info(sData);
	}
	protected void printMsg(String msg) {
		String sData = "组件" + this.getClass().getName() + "执行打印信息：[" + msg+"]";
		log.info(sData);
	}
	protected void printMsg(String key,Object msg) {
		String sData = "组件" + this.getClass().getName() + "执行打印信息1：[" + key  +  "]，信息2：[" + msg+"]";
		log.info(sData);
	}

	/**
	 * 组件失败公共处理
	 */
	protected void compErrorReturn(String retCode, String retMsg) {
	}

	/**
	 * 组件失败公共处理，打印异常堆栈
	 */
	protected void compErrorReturn(String retCode, String retMsg, Exception e) {
		String sData =  "组件返回码:[" + retCode + "]" + "组件返回信息:" + retMsg + e.getMessage() + e.getMessage();
		log.info(sData);
	}

	/**
	 * 组件失败公共处理
	 */
	protected void compErrorReturn(String retCode, String retMsg, Object... params) {
		try {
			String sData =   "组件错误码[" + retCode + "]" + "组件返回信息:" + retMsg + params;
			log.info(sData);
		} catch (Exception e) {
			this.writeRuntimeLog(e.getMessage());
		}
	}

	/**
	 * 校验最少参数个数,不满足条件，就设置组件、交易返回码为 CBP2011，“组件参数个数错 ”
	 */
	protected boolean checkMinParaCountAndSetRespCode(int count, boolean print) {
		int num = parameters.size();
		if (num < count) {
			// 判断是否更新交易级返回码
			String sData =   "组件参数个数错" ;
			log.info(sData);
			return false;
		} else {
			if (print) {
				printCompnentArgs();
			}
			return true;
		}

	}

	protected boolean checkMinParaCountAndSetRespCode(int count) {
		return checkMinParaCountAndSetRespCode(count, true);

	}

	/**
	 * 打印组件参数
	 */
	protected void printCompnentArgs() {
		parameters.forEach((k,v)->{
			log.debug( "组件第" + k + "个参数[" + v + "]");
		});
	}

	/**
	 * 校验参数个数
	 */
	protected boolean checkParaCount(int count) {
		if (parameters.size() != count) {
			return false;
		} else {
			printCompnentArgs();
			return true;
		}
	}

	/**
	 * 记录日志方法，组件至少要进行输入、输出的记录，以便调试排错
	 */
	public void writeRuntimeLog(String message, Object... params) {
	}

	/**
	 * 记录组件需要打印到日志里的信息，以便进行调试排错
	 */
	public void printCompnentLog(String message) {

	}

}
