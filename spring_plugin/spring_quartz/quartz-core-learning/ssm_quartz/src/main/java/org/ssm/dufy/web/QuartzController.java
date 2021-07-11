package org.ssm.dufy.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ssm.dufy.entity.JobEntity;
import org.ssm.dufy.service.QuartzService;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("quartz")
public class QuartzController {

	@Autowired
	private Scheduler quartzScheduler;
	
	@Autowired
	private QuartzService quartzService;

	
	/**
	 * 定时列表页
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value="/listJob")
	public String listJob(HttpServletRequest request,HttpServletResponse response) throws SchedulerException {
		List<JobEntity> jobInfos = this.getSchedulerJobInfo();
		request.setAttribute("jobInfos", jobInfos);
		return "quartz/listjob";
	}
	
	/**
	 * 跳转到新增
	 * 
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="/toAdd")
	public String toAdd(HttpServletRequest request,HttpServletResponse response) throws SchedulerException {
		return "quartz/addjob";
	}
	
	
	/**
	 * 新增job
	 * 
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(HttpServletRequest request,HttpServletResponse response) throws SchedulerException, ClassNotFoundException {
		String jobName = request.getParameter("jobName");
		String jobGroupName = request.getParameter("jobGroupName");
		String triggerName = request.getParameter("triggerName");
		String triggerGroupName = request.getParameter("triggerGroupName");
		String clazz = request.getParameter("clazz");
		Class cls = Class.forName(clazz);
		String cron = request.getParameter("cron");
		quartzService.addJob(jobName, jobGroupName, triggerName, triggerGroupName, cls, cron);
		request.setAttribute("message", "添加任务成功!");
		request.setAttribute("opName", "添加任务!");
		return "quartz/message";
	}

	/**
	 * 跳转到编辑
	 * 
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="/toEdit")
	public String toEdit(HttpServletRequest request,HttpServletResponse response) throws SchedulerException {
		
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		JobDetail jd = quartzScheduler.getJobDetail(jobKey);
		@SuppressWarnings("unchecked")
		List<CronTrigger> triggers = (List<CronTrigger>) quartzScheduler
				.getTriggersOfJob(jobKey);
		CronTrigger trigger = triggers.get(0);
		TriggerKey triggerKey = trigger.getKey();
		String cron = trigger.getCronExpression();
		Map<String, String> pd = new HashMap<String, String>();
		pd.put("jobName", jobKey.getName());
		pd.put("jobGroup", jobKey.getGroup());
		pd.put("triggerName", triggerKey.getName());
		pd.put("triggerGroupName", triggerKey.getGroup());
		pd.put("cron", cron);
		pd.put("clazz", jd.getJobClass().getCanonicalName());

		request.setAttribute("pd", pd);
		request.setAttribute("msg", "edit");
		
		return "quartz/editjob";
	}
	
	
	/**
	 * 编辑job
	 * 
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String edit(HttpServletRequest request,HttpServletResponse response) throws SchedulerException, ClassNotFoundException {
		String jobName = request.getParameter("jobName");
		String jobGroupName = request.getParameter("jobGroupName");
		String triggerName = request.getParameter("triggerName");
		String triggerGroupName = request.getParameter("triggerGroupName");
		String clazz = request.getParameter("clazz");
		Class cls = Class.forName(clazz);
		String cron = request.getParameter("cron");
		
		String oldjobName = request.getParameter("oldjobName");
		String oldjobGroup = request.getParameter("oldjobGroup");
		String oldtriggerName = request.getParameter("oldtriggerName");
		String oldtriggerGroup = request.getParameter("oldtriggerGroup");
		
		boolean result = quartzService.modifyJobTime(oldjobName, oldjobGroup, oldtriggerName, oldtriggerGroup, 
				jobName, jobGroupName, triggerName, triggerGroupName, cron);
		if(result){
			request.setAttribute("message", "修改任务成功!");
		}else{
			request.setAttribute("message", "修改任务失败!");
		}
		request.setAttribute("opName", "更新任务!");
		return "quartz/message";
	}

	@RequestMapping(value="/pauseJob",method=RequestMethod.POST)
	@ResponseBody
	public String pauseJob(@RequestParam("jobName") String jobName,@RequestParam("jobGroupName") String jobGroupName){
		JSONObject json = new JSONObject();
		
		if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName)){
			json.put("status", "wrong");
		}else{
			quartzService.pauseJob(jobName, jobGroupName);
				json.put("status", "success");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping(value="/resumeJob",method=RequestMethod.POST)
	@ResponseBody
	public String resumeJob(@RequestParam("jobName") String jobName,@RequestParam("jobGroupName") String jobGroupName){
		JSONObject json = new JSONObject();
		
		if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName)){
			json.put("status", "wrong");
		}else{
			quartzService.resumeJob(jobName, jobGroupName);
			json.put("status", "success");
		}
		
		return json.toJSONString();
	}
	
	@RequestMapping(value="/deleteJob",method=RequestMethod.POST)
	@ResponseBody
	public String deleteJob(@RequestParam("jobName") String jobName,@RequestParam("jobGroupName") String jobGroupName,
			@RequestParam("triggerName") String triggerName,@RequestParam("triggerGroupName") String triggerGroupName ){
		JSONObject json = new JSONObject();
		
		if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName) || 
				StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(triggerGroupName) ){
			json.put("status", "wrong");
		}else{
			 quartzService.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
			 json.put("status", "success");
		}
		
		return json.toJSONString();
	}
	
	
	private List<JobEntity> getSchedulerJobInfo() throws SchedulerException {
		List<JobEntity> jobInfos = new ArrayList<JobEntity>();
		List<String> triggerGroupNames = quartzScheduler.getTriggerGroupNames();
		for (String triggerGroupName : triggerGroupNames) {
			Set<TriggerKey> triggerKeySet = quartzScheduler
					.getTriggerKeys(GroupMatcher
							.triggerGroupEquals(triggerGroupName));
			for (TriggerKey triggerKey : triggerKeySet) {
				Trigger t = quartzScheduler.getTrigger(triggerKey);
				if (t instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger) t;
					JobKey jobKey = trigger.getJobKey();
					JobDetail jd = quartzScheduler.getJobDetail(jobKey);
					JobEntity jobInfo = new JobEntity();
					jobInfo.setJobName(jobKey.getName());
					jobInfo.setJobGroup(jobKey.getGroup());
					jobInfo.setTriggerName(triggerKey.getName());
					jobInfo.setTriggerGroupName(triggerKey.getGroup());
					jobInfo.setCronExpr(trigger.getCronExpression());
					jobInfo.setNextFireTime(trigger.getNextFireTime());
					jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
					jobInfo.setStartTime(trigger.getStartTime());
					jobInfo.setEndTime(trigger.getEndTime());
					jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
					// jobInfo.setDuration(Long.parseLong(jd.getDescription()));
					Trigger.TriggerState triggerState = quartzScheduler
							.getTriggerState(trigger.getKey());
					jobInfo.setJobStatus(triggerState.toString());// NONE无,
																	// NORMAL正常,
																	// PAUSED暂停,
																	// COMPLETE完全,
																	// ERROR错误,
																	// BLOCKED阻塞
					JobDataMap map = quartzScheduler.getJobDetail(jobKey)
							.getJobDataMap();
					if (null != map&&map.size() != 0) {
						jobInfo.setCount(Integer.parseInt((String) map
								.get("count")));
						jobInfo.setJobDataMap(map);
					} else {
						jobInfo.setJobDataMap(new JobDataMap());
					}
					jobInfos.add(jobInfo);
				}
			}
		}
		return jobInfos;
	}
	
}
