package com.erp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.BackupScheduleConfig;
import com.erp.model.Log;
import com.erp.service.IDbBackUpService;
import com.erp.shiro.ShiroUser;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.XMLFactory;
import com.jun.plugin.utils.biz.PageUtil;

@SuppressWarnings("unchecked")
@Service("dbBackUpService")
public class DbBackUpServiceImpl implements IDbBackUpService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private JobDetail backupTask= new JobDetailImpl("task","taskGroup",BackupScheduleServiceImpl.class);
	private XMLFactory xmlFactory=new XMLFactory(BackupScheduleConfig.class);
	private static String basePath =System.getProperty("erp");
	private static String xmlPath=basePath+"common"+File.separator+"dbBackUpInit.xml";
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public void onApplicationEvent(ContextRefreshedEvent event )
	{
		if (event instanceof ContextRefreshedEvent) {
            System.out.println("Spring容器初始化完成, 开始检查是否需要启动定时备份数据调度器");
            BackupScheduleConfig config = getBackupScheduleConfig();
            if (config != null && "Y".equals(config.getScheduleEnabled())) {
               schedule(config.getScheduleHour(),config.getScheduleMinute(),config.getScheduleEnabled());
               System.out.println("启动定时备份数据调度器");
            }else{
            	System.out.println("没有设置定时备份数据任务");
            }
        }
	}
    /* (非 Javadoc) 
    * <p>Title: getBackupScheduleConfig</p> 
    * <p>Description:获取备份数据库调度配置 </p> 
    * @return 
    * @see com.erp.service.DbBackUpService#getBackupScheduleConfig() 
    */
    public BackupScheduleConfig getBackupScheduleConfig(){  
    		try
			{
    			return xmlFactory.unmarshal(new FileInputStream(new File(xmlPath)));
			} catch (FileNotFoundException e)
			{
				System.out.println("xml文件未找到");
			}
    		return null;
    }
    
    /* (非 Javadoc) 
    * <p>Title: unSchedule</p> 
    * <p>Description:取消定时任务 并重建定时任务 </p> 
    * @return 
    * @see com.erp.service.DbBackUpService#unSchedule() 
    */
    public String unSchedule(){        
        try {
            BackupScheduleConfig config=getBackupScheduleConfig();
            if(config!=null){
                config.setScheduleEnabled("N");
                //String xmlString = xmlFactory.marshal(config);
                //xmlFactory.stringXMLToFile(xmlPath, xmlString);
                System.out.println("禁用定时重建配置对象");
            }else{
                String tip="还没有设置定时备份数据任务";
                System.out.println(tip);
                return tip;
            }
            Scheduler sched = sf.getScheduler();
            sched.deleteJob(backupTask.getKey());
            sched.shutdown();
            String tip="删除定时备份数据任务，任务名为：" + backupTask.getDescription() + ",全名为: " + backupTask.getDescription();
            System.out.println(tip);
            return tip;
        } catch (SchedulerException ex) {
            String tip="删除定时备份数据任务失败，原因："+ex.getMessage();
            System.out.println(tip);
            return tip;
        }
    }
    
    /* (非 Javadoc) 
    * <p>Title: schedule</p> 
    * <p>Description: 定时备份数据库（24小时制）</p> 
    * @param hour 小时
    * @param minute 分钟
    * @param scheduleEnabled
    * @return 
    * @see com.erp.service.DbBackUpService#schedule(int, int, java.lang.String) 
    */
    public String schedule(int hour, int minute,String scheduleEnabled) {
        BackupScheduleConfig scheduleConfig = getBackupScheduleConfig();
        if (scheduleConfig == null) {
            //新建配置对象
            BackupScheduleConfig config = new BackupScheduleConfig();
            config.setScheduleHour(hour);
            config.setScheduleMinute(minute);
            config.setScheduleEnabled("Y");
            String xmlString = xmlFactory.marshal(config);
            xmlFactory.stringXMLToFile(xmlPath, xmlString);
        } else {
            //修改配置对象
            scheduleConfig.setScheduleHour(hour);
            scheduleConfig.setScheduleMinute(minute);
            scheduleConfig.setScheduleEnabled(scheduleEnabled);
            String xmlString = xmlFactory.marshal(scheduleConfig);
            xmlFactory.stringXMLToFile(xmlPath, xmlString);
        }

        String expression = "0 " + minute + " " + hour + " * * ?";
       // String expression = "5/15 * * * * ?";
       try {
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger()
            		.withIdentity("trigger1", "group1").withDescription("定时触发器,时间为：" + hour + ":" + minute)
            		//设置Cron表达式 ，从0秒开始 每隔20秒执行一次
            		.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
            		.build();
            Scheduler sched = sf.getScheduler();
            sched.deleteJob(backupTask.getKey());
            sched.scheduleJob(backupTask, trigger);
            sched.start();
            String tip = "删除上一次的任务，任务名为：" + backupTask.getDescription() + ",全名为: " + backupTask.getDescription();
            System.out.println(tip);
            String taskState = "定时备份数据任务执行频率为每天，时间（24小时制）" + hour + ":" + minute;
            System.out.println(taskState);
            return taskState;
        } catch (Exception  e) {
            String tip = "定时备份数据设置失败，原因：" + e.getMessage();
            System.out.println(tip);
            return tip;
        }
    }
    
	/* (非 Javadoc) 
	* <p>Title: handSchedule</p> 
	* <p>Description:手动备份</p> 
	* @return 
	* @see com.erp.service.DbBackUpService#handSchedule() 
	*/
	public boolean handSchedule(HttpServletRequest request)
	{
		String filename = Constants.dbBackUp();
		String sqlName = Constants.BASE_PATH +"attachment" +File.separator+ "dbBackUp" + File.separator + filename;
		return addLog(sqlName,filename,false);
	}
	
	/* (非 Javadoc) 
	* <p>Title: findLogsAllList</p> 
	* <p>Description: 查询数据库备份日志</p> 
	* @param map
	* @param pageUtil
	* @return 
	* @see com.erp.service.DbBackUpService#findLogsAllList(java.util.Map, com.erp.util.PageUtil) 
	*/
	public List<Log> findLogsAllList(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="from Log t where t.type=1 ";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql+=" order by t.logId desc";
		return publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}
	
	/* (非 Javadoc) 
	* <p>Title: getCount</p> 
	* <p>Description: 查询所有备份数量</p> 
	* @param map
	* @param pageUtil
	* @return 
	* @see com.erp.service.DbBackUpService#getCount(java.util.Map, com.erp.util.PageUtil) 
	*/
	public Long getCount(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="select count(*) from Log t where t.type=1 ";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql+=" order by t.logId desc";
		//dbBackUp();
		return  publicDao.count(hql, map);
	}
	
	/* (非 Javadoc) 
	* <p>Title: addLog</p> 
	* <p>Description:增加数据库备份日志 </p> 
	* @param path
	* @param fileName
	* @param isSystem
	* @return 
	* @see com.erp.service.DbBackUpService#addLog(java.lang.String, java.lang.String, boolean) 
	*/
	public boolean addLog(String path,String fileName,boolean isSystem)
	{
		Log log = new Log();
		log.setLogDate(new Date());
		log.setType(1);
		if (isSystem)
		{
			log.setName("system");
			log.setMac("**************");
			log.setIp("**************");
		}else {
			ShiroUser user = Constants.getCurrendUser();
			log.setUserId(user.getUserId());
			log.setName(user.getAccount());
			log.setMac(Constants.getMacAddr());
			log.setIp(Constants.getIpAddr());
		}
		log.setEventName("数据备份");
		log.setEventRecord(path);
		log.setObjectId(fileName);
		publicDao.save(log);
		return true;
	}
	
	
	public static String getSeparator(){
		  return System.getProperties().getProperty("file.separator");
	}

	 
}
