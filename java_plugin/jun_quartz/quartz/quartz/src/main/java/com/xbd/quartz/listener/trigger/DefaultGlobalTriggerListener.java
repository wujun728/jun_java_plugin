package com.xbd.quartz.listener.trigger;

import com.xbd.quartz.listener.AbstractTriggerListener;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 默认全局TriggerListener
 * </p>
 *
 * @author 小不点
 */
public class DefaultGlobalTriggerListener extends AbstractTriggerListener {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public String getName() {
		return "defaultGlobalTriggerListener";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {

	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		
		log.info("任务：{}执行完成，开始执行：{}，上次执行：{}，下次执行：{}", new Object[] { trigger.getKey().toString(), trigger.getStartTime(), trigger.getPreviousFireTime(), trigger.getNextFireTime() });
	}
	
}
