/**
 * 
 */
package com.opensource.nredis.proxy.monitor.api;


import java.lang.management.ClassLoadingMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Line;
import com.opensource.nredis.proxy.monitor.api.utils.ConfigUtils;
import com.opensource.nredis.proxy.monitor.date.utils.DateBase;
import com.opensource.nredis.proxy.monitor.enums.TomcatStatusEnums;
import com.opensource.nredis.proxy.monitor.jvm.tools.CPUBean;
import com.opensource.nredis.proxy.monitor.jvm.tools.HeapMemroryGarbage;
import com.opensource.nredis.proxy.monitor.jvm.tools.MemrorySpaceBean;
import com.opensource.nredis.proxy.monitor.jvm.tools.MonitorJVM;
import com.opensource.nredis.proxy.monitor.jvm.tools.QuartzRuntimeBean;
import com.opensource.nredis.proxy.monitor.jvm.tools.TomcatThreadBean;
import com.opensource.nredis.proxy.monitor.model.SystemApplication;
import com.opensource.nredis.proxy.monitor.model.SystemComponent;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.EChartsObject;
import com.opensource.nredis.proxy.monitor.platform.NormalAreaStyle;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationService;
import com.opensource.nredis.proxy.monitor.service.ISystemComponentService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
 * @author liubing
 * 系统首页监控图
 */
@Controller
public class SystemIndexController {
	
	private Logger logger = Logger.getLogger(SystemIndexController.class);

	@Autowired
	private ISystemApplicationService systemApplicationService;
	
	@Autowired
	private ISystemComponentService systemComponentService;
	
	private static Integer tomcatFlag=1;
	
	private static Integer quartzFlag=2;
	
	//--------------------------------tomcat thread--------------
	private List<String> threadTime=new ArrayList<String>();	
	private Map<Integer,List<Long>> tomcatCurrentThreadCountMap=new ConcurrentHashMap<Integer, List<Long>>();
	private Map<Integer,List<Long>> tomcatCurrentThreadsBusyMap=new ConcurrentHashMap<Integer, List<Long>>();
	private Map<Integer,List<Long>> tomcatMaxThreadMap=new ConcurrentHashMap<Integer, List<Long>>();

	//------------------------------jvm thread---------------------
	private Map<Integer,List<Integer>> jvmThreadThreadCountsMap=new ConcurrentHashMap<Integer, List<Integer>>();
	private Map<Integer,List<Integer>> jvmThreadDeadThreadCountsMap=new ConcurrentHashMap<Integer, List<Integer>>();
	//----------------------------jvm classLoader--------------------
	private Map<Integer,List<Integer>> jvmClassLoaderLoadedClassCountMap=new ConcurrentHashMap<Integer, List<Integer>>();
	//------------------------------cpu -----------------------------------
	private Map<Integer,List<Double>> CPUCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	//------------------------------jvm heap -----------------------------------
	private Map<Integer,List<Double>>jvmHeapCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	//-------------------------------jvm各个区域使用率---------------------------------------
	private Map<Integer,List<Double>> jvmEdenCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	private Map<Integer,List<Double>> jvmSurvivorCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	private Map<Integer,List<Double>>jvmOldCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	private Map<Integer,List<Double>> jvmPermCountMap=new ConcurrentHashMap<Integer, List<Double>>();
	
	
	@RequestMapping(value = "/{id}/searchSystemComponentObject", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchSystemComponentObject(@PathVariable Integer id)throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Map<String, Integer> result=new HashMap<String, Integer>();
		List<SystemComponent> systemComponents=systemComponentService.getListByApplicationId(id);
		if(systemComponents!=null&&systemComponents.size()>0){
			for(SystemComponent systemComponent:systemComponents){
				if(StringUtil.isNotEmpty(systemComponent.getComponentValue())&&Integer.parseInt(systemComponent.getComponentValue())==quartzFlag){
					result.put("quartz", 1);//有
				}
				if(StringUtil.isNotEmpty(systemComponent.getComponentValue())&&Integer.parseInt(systemComponent.getComponentValue())==tomcatFlag){
					result.put("tomcat", 1);//有
				}
			}
		}else{
			result.put("quartz", 0);//无
			result.put("tomcat", 0);//无
		}
		responseObject.setData(result);
		responseObject.setStatus(RestStatus.SERVER_ERROR.code);
		responseObject.setMessage(RestStatus.SERVER_ERROR.message);
		return responseObject;
	}
	/**
	 * 统一时间点
	 * 查询监控报表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchEChartsObject", method = RequestMethod.GET)
	@ResponseBody
	public EChartsObject searchEChartsObject(@PathVariable Integer id)throws Exception{
		SystemApplication systemApplication=systemApplicationService.getEntityById(id);
		Integer tomcat=0,quartz=0;//默认无关联组件
		List<SystemComponent> systemComponents=systemComponentService.getListByApplicationId(id);
		if(systemComponents!=null&&systemComponents.size()>0){
			for(SystemComponent systemComponent:systemComponents){
				if(StringUtil.isNotEmpty(systemComponent.getComponentValue())&&Integer.parseInt(systemComponent.getComponentValue())==quartzFlag){
					quartz=1;//有关联控件
				}
				if(StringUtil.isNotEmpty(systemComponent.getComponentValue())&&Integer.parseInt(systemComponent.getComponentValue())==tomcatFlag){
					tomcat=1;//有关联控件
				}
				
			}
		}
		return getSelfCPUUsed(systemApplication,tomcat,quartz);
	}
	
	/**
	 * 获取cpu使用消息
	 * @param systemApplication
	 * @param tomcatFlag 1:成功,2:失败
	 * @param quartzFlag 1：成功,2:失败
	 * @return
	 * @throws Exception
	 */
	private EChartsObject getSelfCPUUsed(SystemApplication systemApplication,Integer tomcatFlag,Integer quartzFlag)throws Exception{
		EChartsObject chartsObject=new EChartsObject();
		Long start = System.currentTimeMillis();
		MonitorJVM monitorJVM=new MonitorJVM(systemApplication.getJmxHost(), systemApplication.getJmxPort(), systemApplication.getJmxUserName(), systemApplication.getJmxPassWord());
		CPUBean cPUBean=monitorJVM.getProcessCpuTime();
		
		try {  
				if(quartzFlag!=null&&quartzFlag==1){
					chartsObject.setQuartzRuntimeDataGridObject(searchQuartzRuntimeBeans(systemApplication,monitorJVM));
				}
				if(tomcatFlag!=null&&tomcatFlag==1){
					chartsObject.setTomcatThreadOption(searchSelfTomcatThread(systemApplication,monitorJVM));
				}
				chartsObject.setHeapMemroryGarbageObject(searchHeapMemroryGarbage(systemApplication,monitorJVM));
				chartsObject.setJvmInfoOption(searchSelfJVMInfo(systemApplication,monitorJVM));
				chartsObject.setJvmClassLoaderOption(searchSelfJVMClassLoader(systemApplication,monitorJVM));
				chartsObject.setJvmThreadOption(searchSelfJVMThread(systemApplication,monitorJVM));
				chartsObject.setJvmUsedOption(searchSelfJVMUsed(systemApplication,monitorJVM));
	        } catch (InterruptedException e) {  
	            logger.error("InterruptedException occurred while MemoryCollector sleeping..."); 
	        }
		Long end = System.currentTimeMillis();
		CPUBean cPUBean1 =monitorJVM.getProcessCpuTime();
		monitorJVM.close();
		double ratio = (cPUBean1.getProcessCpuTime()-cPUBean.getProcessCpuTime())/1000000.0/(end-start)/cPUBean.getAvailableProcessors();
		String result =new java.text.DecimalFormat("#.00").format(ratio*100);
		List<Double> CPUCount=new ArrayList<Double>();
		if(CPUCountMap.containsKey(systemApplication.getId())){
			CPUCount=CPUCountMap.get(systemApplication.getId());
		}
		
		if(CPUCount!=null&&CPUCount.size()>9){//不能超过 10个点时间段
			CPUCount.subList(0, CPUCount.size()-9).clear();
		}
		CPUCount.add(Double.parseDouble(result));
		CPUCountMap.put(systemApplication.getId(), CPUCount);
		chartsObject.setCpuUsedOption(getCPUOption(systemApplication));
		return chartsObject;
	}
	
	/**
	 * 获取系统应用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchIndexSystemApplications", method = RequestMethod.GET)
	@ResponseBody
	public  DataGridObject searchSystemApplication() throws Exception{
		DataGridObject dataGridObject=new DataGridObject();
		String systemName=getSystemName();
		SystemApplication systemApplication=new SystemApplication();
		systemApplication.setApplicationName(systemName);
		List<SystemApplication> systemApplications=systemApplicationService.queryEntityList(systemApplication);
		dataGridObject.setTotal(systemApplications.size());
		dataGridObject.setRows(systemApplications);
		return dataGridObject;
	}
	
	/**
	 * 获取系统名称
	 * @return
	 * @throws Exception
	 */
	private String getSystemName() throws Exception{
		Properties config = ConfigUtils.getProperties();
		String systemName=config.getProperty("system.name");
		return systemName;
	}
	
	
	private Option getCPUOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("CPU使用率");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		categoryAxis.setAxisLabel(new AxisLabel().formatter("{value} %"));
		tomcatTreadOption.yAxis(categoryAxis);
		Line line1=new Line();
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle);
		line1.setName("CPU使用率");
		line1.setType(SeriesType.line);
		line1.setData(CPUCountMap.get(systemApplication.getId()));
		
		tomcatTreadOption.series(line1);
		return tomcatTreadOption;
	}
	
	/**
	 * 获取定时器
	 * @return
	 * @throws Exception
	 */
	private DataGridObject searchQuartzRuntimeBeans(SystemApplication systemApplication,MonitorJVM monitorJVM) throws Exception{
		DataGridObject dataGridObject=new DataGridObject();
		List<String> keyWords=new ArrayList<String>();
		List<SystemComponent> systemComponents=systemComponentService.getListByApplicationId(systemApplication.getId());
		for(SystemComponent systemComponent:systemComponents){
			if(systemComponent.getKeyConfig().contains("quartz")){
				keyWords.add(systemComponent.getKeyConfig());
			}
		}
		List<QuartzRuntimeBean> quartzRuntimeBeans=new ArrayList<QuartzRuntimeBean>();
		for(String keyword:keyWords){
			List<QuartzRuntimeBean> runtimeBeans=monitorJVM.getQuartzRuntimeBeans(keyword);
			quartzRuntimeBeans.addAll(runtimeBeans);
		}
		for(QuartzRuntimeBean quartzRuntimeBean:quartzRuntimeBeans){
			quartzRuntimeBean.setStatusName(TomcatStatusEnums.getMessage(quartzRuntimeBean.getStatus()));
		}
		
		dataGridObject.setTotal(quartzRuntimeBeans.size());
		dataGridObject.setRows(quartzRuntimeBeans);
		return dataGridObject;
	}
	
	/**
	 * 获取垃圾回收器
	 * @return
	 * @throws Exception
	 */

	private  DataGridObject searchHeapMemroryGarbage(SystemApplication systemApplication,MonitorJVM monitorJVM) throws Exception{
		DataGridObject dataGridObject=new DataGridObject();
		HeapMemroryGarbage heapMemroryGarbage=monitorJVM.getHeapMemroryGarbage();

		List<HeapMemroryGarbage> heapMemroryGarbages=new ArrayList<HeapMemroryGarbage>();
		heapMemroryGarbages.add(heapMemroryGarbage);
		dataGridObject.setRows(heapMemroryGarbages);
		dataGridObject.setTotal(1);
		return dataGridObject;
	}
	/**
	 * 获取内存各个区域使用率
	 * @return
	 * @throws Exception
	 */
	private Option searchSelfJVMInfo(SystemApplication systemApplication,MonitorJVM monitorJVM)throws Exception{
		MemrorySpaceBean memrorySpaceBean=monitorJVM.getMemrorySpaceInfo();
		String currentTime=DateBase.formatDate(new Date(), DateBase.DATE_PATTERN_TIME);
		
		List<Double> jvmEdenCount=new ArrayList<Double>();
		List<Double> jvmSurvivorCount=new ArrayList<Double>();
		List<Double> jvmOldCount=new ArrayList<Double>();
		List<Double> jvmPermCount=new ArrayList<Double>();
		if(CPUCountMap.containsKey(systemApplication.getId())){
			jvmEdenCount=jvmEdenCountMap.get(systemApplication.getId());	
		}
		if(jvmSurvivorCountMap.containsKey(systemApplication.getId())){
			jvmSurvivorCount=jvmSurvivorCountMap.get(systemApplication.getId());
		}
		if(jvmOldCountMap.containsKey(systemApplication.getId())){
			jvmOldCount=jvmOldCountMap.get(systemApplication.getId());
		}
		if(jvmPermCountMap.containsKey(systemApplication.getId())){
			jvmPermCount=jvmPermCountMap.get(systemApplication.getId());
		}
		if(threadTime!=null&&threadTime.size()>9){
			threadTime.subList(0, threadTime.size()-9).clear();
		}
		
		if(jvmEdenCount!=null&&jvmEdenCount.size()>9){//不能超过 10个点时间段
			
			jvmEdenCount.subList(0, jvmEdenCount.size()-9).clear();
			
		}
		if(jvmSurvivorCount!=null&&jvmSurvivorCount.size()>9){//不能超过 10个点时间段
			jvmSurvivorCount.subList(0, jvmSurvivorCount.size()-9).clear();

		}
		
		if(jvmOldCount!=null&&jvmOldCount.size()>9){//不能超过 10个点时间段
			jvmOldCount.subList(0, jvmOldCount.size()-9).clear();
		}
		if(jvmPermCount!=null&&jvmPermCount.size()>9){//不能超过 10个点时间段
			jvmPermCount.subList(0, jvmPermCount.size()-9).clear();
		}
		threadTime.add(currentTime);
		double edenRatio =memrorySpaceBean.getEdenUsed()*100/memrorySpaceBean.getEdenCommitted();
		String edenResult =new java.text.DecimalFormat("#.00").format(edenRatio);
		jvmEdenCount.add(Double.parseDouble(edenResult));
		
		double survivorRatio =memrorySpaceBean.getSurvivorUsed()*100/memrorySpaceBean.getSurvivorCommitted();
		String survivorResult =new java.text.DecimalFormat("#.00").format(survivorRatio);
		jvmSurvivorCount.add(Double.parseDouble(survivorResult));
		
		double oldRatio =memrorySpaceBean.getOldUsed()*100/memrorySpaceBean.getOldCommitted();
		String oldResult =new java.text.DecimalFormat("#.00").format(oldRatio);
		jvmOldCount.add(Double.parseDouble(oldResult));
		
		double permRatio =memrorySpaceBean.getPermUsed()*100/memrorySpaceBean.getPermCommitted();
		String permResult =new java.text.DecimalFormat("#.00").format(permRatio);
		jvmPermCount.add(Double.parseDouble(permResult));
		jvmEdenCountMap.put(systemApplication.getId(), jvmEdenCount);
		jvmSurvivorCountMap.put(systemApplication.getId(), jvmSurvivorCount);
		jvmOldCountMap.put(systemApplication.getId(), jvmOldCount);
		jvmPermCountMap.put(systemApplication.getId(), jvmPermCount);
		return getJVMInfoOption(systemApplication);
	}
	
	private Option getJVMInfoOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("Eden使用率","Survivor使用率","Old使用率","Perm使用率");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		categoryAxis.setAxisLabel(new AxisLabel().formatter("{value} %"));
		tomcatTreadOption.yAxis(categoryAxis);
		
		Line line1=new Line();
		line1.setName("Eden使用率");
		line1.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle);
		line1.setData(jvmEdenCountMap.get(systemApplication.getId()));
		
		Line line2=new Line();
		line2.setName("Survivor使用率");
		line2.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle2=new NormalAreaStyle();
		nomalAreaStyle2.setNormal("{}");
		line2.setAreaStyle(nomalAreaStyle2);
		line2.setData(jvmSurvivorCountMap.get(systemApplication.getId()));
		
		Line line3=new Line();
		line3.setName("Old使用率");
		line3.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle3=new NormalAreaStyle();
		nomalAreaStyle3.setNormal("{}");
		line3.setAreaStyle(nomalAreaStyle3);
		line3.setData(jvmOldCountMap.get(systemApplication.getId()));
		
		Line line4=new Line();
		line4.setName("Perm使用率");
		line4.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle4=new NormalAreaStyle();
		nomalAreaStyle4.setNormal("{}");
		line4.setAreaStyle(nomalAreaStyle4);
		line4.setData(jvmPermCountMap.get(systemApplication.getId()));
		
		tomcatTreadOption.series(line1,line2,line3,line4);
		return tomcatTreadOption;
	}
	
	/**
	 * 获取内存堆使用率
	 * @return
	 * @throws Exception
	 */

	private Option searchSelfJVMUsed(SystemApplication systemApplication,MonitorJVM monitorJVM)throws Exception{
		MemoryMXBean memoryMXBean=monitorJVM.getMemoryMXBean();
		List<Double> jvmHeapCount=new ArrayList<Double>();
		if(jvmHeapCountMap.containsKey(systemApplication.getId())){
			jvmHeapCount=jvmHeapCountMap.get(systemApplication.getId());
		}
		if(jvmHeapCount!=null&&jvmHeapCount.size()>9){//不能超过 10个点时间段
			jvmHeapCount.subList(0, jvmHeapCount.size()-9).clear();
		}
		double ratio =memoryMXBean.getHeapMemoryUsage().getUsed()*100/memoryMXBean.getHeapMemoryUsage().getCommitted();
		String result =new java.text.DecimalFormat("#.00").format(ratio);
		jvmHeapCount.add(Double.parseDouble(result));
		jvmHeapCountMap.put(systemApplication.getId(), jvmHeapCount);
		return getJVMHEAPOption(systemApplication);
	}
	
	private Option getJVMHEAPOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("堆使用率");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		categoryAxis.setAxisLabel(new AxisLabel().formatter("{value} %"));
		tomcatTreadOption.yAxis(categoryAxis);
		Line line1=new Line();
		line1.setName("堆使用率");
		line1.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle);
		line1.setData(jvmHeapCountMap.get(systemApplication.getId()));
		
		tomcatTreadOption.series(line1);
		return tomcatTreadOption;
	}
	
	
	
	/**
	 * 获取jvm classLoader
	 * @return
	 * @throws Exception
	 */

	private Option searchSelfJVMClassLoader(SystemApplication systemApplication,MonitorJVM monitorJVM)throws Exception{
		ClassLoadingMXBean classLoadingMXBean=monitorJVM.getClassLoadingMXBean();
		List<Integer> jvmClassLoaderLoadedClassCount=new ArrayList<Integer>();
		if(jvmClassLoaderLoadedClassCountMap.containsKey(systemApplication.getId())){
			jvmClassLoaderLoadedClassCount=jvmClassLoaderLoadedClassCountMap.get(systemApplication.getId());
		}
		if(jvmClassLoaderLoadedClassCount!=null&&jvmClassLoaderLoadedClassCount.size()>9){//不能超过 10个点时间段
			jvmClassLoaderLoadedClassCount.subList(0, jvmClassLoaderLoadedClassCount.size()-9).clear();
		}
		jvmClassLoaderLoadedClassCount.add(classLoadingMXBean.getLoadedClassCount());
		jvmClassLoaderLoadedClassCountMap.put(systemApplication.getId(), jvmClassLoaderLoadedClassCount);
		return getJVMClassLoaderOption(systemApplication);
	}
	
	private Option getJVMClassLoaderOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("加载数");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		tomcatTreadOption.yAxis(categoryAxis);
		Line line1=new Line();
		line1.setName("加载数");
		line1.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle);
		line1.setData(jvmClassLoaderLoadedClassCountMap.get(systemApplication.getId()));
		
		tomcatTreadOption.series(line1);
		return tomcatTreadOption;
	}
	
	/**
	 * JVM线程监控
	 * @return
	 * @throws Exception
	 */

	private Option searchSelfJVMThread(SystemApplication systemApplication,MonitorJVM monitorJVM)throws Exception{
		ThreadMXBean threadMXBean=monitorJVM.getThreadMXBean();
		List<Integer> jvmThreadThreadCounts=new ArrayList<Integer>();
		List<Integer> jvmThreadDeadThreadCounts=new ArrayList<Integer>();
		
		if(jvmThreadThreadCountsMap.containsKey(systemApplication.getId())){
			jvmThreadThreadCounts=jvmThreadThreadCountsMap.get(systemApplication.getId());
		}
		if(jvmThreadDeadThreadCountsMap.containsKey(systemApplication.getId())){
			jvmThreadDeadThreadCounts=jvmThreadDeadThreadCountsMap.get(systemApplication.getId());
		}
		
		if(jvmThreadThreadCounts!=null&&jvmThreadThreadCounts.size()>9){//不能超过 10个点时间段
			jvmThreadThreadCounts.subList(0, jvmThreadThreadCounts.size()-9).clear();
		}
		if(jvmThreadDeadThreadCounts!=null&&jvmThreadDeadThreadCounts.size()>9){//不能超过 10个点时间段
			jvmThreadDeadThreadCounts.subList(0, jvmThreadDeadThreadCounts.size()-9).clear();
		}
		jvmThreadThreadCounts.add(threadMXBean.getThreadCount());
		if(threadMXBean.findDeadlockedThreads()==null){
			jvmThreadDeadThreadCounts.add(0);
		}else{
			jvmThreadDeadThreadCounts.add(threadMXBean.findDeadlockedThreads().length);
		}
		jvmThreadThreadCountsMap.put(systemApplication.getId(), jvmThreadThreadCounts);
		jvmThreadDeadThreadCountsMap.put(systemApplication.getId(), jvmThreadDeadThreadCounts);

		return getJVMTreadOption(systemApplication);
	}
	
	private Option getJVMTreadOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("活跃线程","死锁线程");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		tomcatTreadOption.yAxis(categoryAxis);
		
		Line line=new Line();
		line.setName("活跃线程");
		line.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line.setAreaStyle(nomalAreaStyle);
		line.setData(jvmThreadThreadCountsMap.get(systemApplication.getId()));
		
		Line line1=new Line();
		line1.setName("死锁线程");
		line1.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle1=new NormalAreaStyle();
		nomalAreaStyle1.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle1);
		line1.setData(jvmThreadDeadThreadCountsMap.get(systemApplication.getId()));
		
		tomcatTreadOption.series(line,line1);
		return tomcatTreadOption;
	}
	
	/**
	 * 获取tomcat
	 * @return
	 * @throws Exception
	 */
	private Option searchSelfTomcatThread(SystemApplication systemApplication,MonitorJVM monitorJVM)throws Exception{
		//SystemApplication systemApplication=systemApplicationService.getSystemApplicationByHostAndPort(jmxHost, jmxPort);
		String keyWord="";
		List<SystemComponent> systemComponents=systemComponentService.getListByApplicationId(systemApplication.getId());
		for(SystemComponent systemComponent:systemComponents){
			if(systemComponent.getKeyConfig().contains(MonitorJVM.TOMCAT_THREAD_POOL)){
				keyWord=systemComponent.getKeyConfig();
				break;
			}
		}
		TomcatThreadBean tomcatThreadBean=new TomcatThreadBean();//获取本地服务，默认只有一个值
		List<TomcatThreadBean> threadBeans=monitorJVM.getTomcatRuntimeBeans(keyWord, String.valueOf(systemApplication.getApplicationPort()));
		tomcatThreadBean =threadBeans.get(0);
		
		List<Long> tomcatCurrentThreadCount=new ArrayList<Long>();
		List<Long> tomcatCurrentThreadsBusy=new ArrayList<Long>();
		List<Long> tomcatMaxThread=new ArrayList<Long>();

		if(tomcatCurrentThreadCountMap.containsKey(systemApplication.getId())){
			tomcatCurrentThreadCount=tomcatCurrentThreadCountMap.get(systemApplication.getId());
		}
		if(tomcatCurrentThreadsBusyMap.containsKey(systemApplication.getId())){
			tomcatCurrentThreadsBusy=tomcatCurrentThreadsBusyMap.get(systemApplication.getId());
		}
		if(tomcatMaxThreadMap.containsKey(systemApplication.getId())){
			tomcatMaxThread=tomcatMaxThreadMap.get(systemApplication.getId());
		}
		
		if(tomcatCurrentThreadCount!=null&&tomcatCurrentThreadCount.size()>9){//不能超过 10个点时间段
			tomcatCurrentThreadCount.subList(0, tomcatCurrentThreadCount.size()-9).clear();
		}
		if(tomcatCurrentThreadsBusy!=null&&tomcatCurrentThreadsBusy.size()>9){//不能超过 10个点时间段
			tomcatCurrentThreadsBusy.subList(0, tomcatCurrentThreadsBusy.size()-9).clear();
		}
		if(tomcatMaxThread!=null&&tomcatMaxThread.size()>9){//不能超过 10个点时间段
			tomcatMaxThread.subList(0, tomcatMaxThread.size()-9).clear();
		}
		tomcatCurrentThreadCount.add(tomcatThreadBean.getCurrentThreadCount());//当前线程
		tomcatCurrentThreadsBusy.add(tomcatThreadBean.getCurrentThreadsBusy());//繁忙线程
		tomcatMaxThread.add(tomcatThreadBean.getMaxThreads());//最大线程
		
		tomcatCurrentThreadCountMap.put(systemApplication.getId(), tomcatCurrentThreadCount);
		tomcatCurrentThreadsBusyMap.put(systemApplication.getId(), tomcatCurrentThreadsBusy);
		tomcatMaxThreadMap.put(systemApplication.getId(), tomcatMaxThread);
		
		Option tomcatTreadOption= getTomcatTreadOption(systemApplication);
		return tomcatTreadOption;
	}
	
	private Option getTomcatTreadOption(SystemApplication systemApplication){
		Option tomcatTreadOption = getOption(systemApplication.getApplicationName(),systemApplication.getApplicationHost());
		tomcatTreadOption.tooltip(Trigger.axis).legend().data("当前线程","繁忙线程","最大线程");
		ValueAxis valueAxis=new ValueAxis();
		valueAxis.setType(AxisType.category);
		valueAxis.setBoundaryGap(false);
		valueAxis.setData(threadTime);
		tomcatTreadOption.xAxis(valueAxis);
		
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setType(AxisType.value);
		tomcatTreadOption.yAxis(categoryAxis);
		
		Line line=new Line();
		line.setName("当前线程");
		line.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle=new NormalAreaStyle();
		nomalAreaStyle.setNormal("{}");
		line.setAreaStyle(nomalAreaStyle);
		line.setData(tomcatCurrentThreadCountMap.get(systemApplication.getId()));
		
		Line line1=new Line();
		line1.setName("繁忙线程");
		line1.setType(SeriesType.line);
		NormalAreaStyle nomalAreaStyle1=new NormalAreaStyle();
		nomalAreaStyle1.setNormal("{}");
		line1.setAreaStyle(nomalAreaStyle1);
		line1.setData(tomcatCurrentThreadsBusyMap.get(systemApplication.getId()));
		
		Line line2=new Line();
		line2.setName("最大线程");
		line2.setType(SeriesType.line);
		line2.setData(tomcatMaxThreadMap.get(systemApplication.getId()));
		NormalAreaStyle nomalAreaStyle2=new NormalAreaStyle();
		nomalAreaStyle2.setNormal("{}");
		line2.setAreaStyle(nomalAreaStyle2);
		tomcatTreadOption.series(line,line1,line2);
		return tomcatTreadOption;
	}
	
	/**
	 * 获取echartz重要初始化选项
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
