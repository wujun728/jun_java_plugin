package org.itkk.udf.scheduler.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.scheduler.IListenerEvent;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * ClassName: SchListener
 * </p>
 * <p>
 * Description: 计划任务监听器
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2014年1月24日
 * </p>
 */
public class SchListener implements SchedulerListener {

    /**
     * <p>
     * Field CHAR_1: 斜杠
     * </p>
     */
    private static final String CHAR_1 = "/";

    /**
     * <p>
     * Field ZHUOYE: 作业
     * </p>
     */
    private static final String ZHUOYE = "作业";

    /**
     * 描述 : listenerLog
     */
    @Autowired(required = false)
    private IListenerEvent listenerLog;

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        String logMsg = "(triggerPaused)" + triggerKey.getName() + "/" + triggerKey.getGroup() + "被暂停了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        String logMsg =
                "(triggerResumed)" + triggerKey.getName() + CHAR_1 + triggerKey.getGroup() + "被恢复了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        String logMsg = "(jobScheduled)" + ZHUOYE + trigger.getJobKey().getName() + CHAR_1
                + trigger.getJobKey().getGroup() + "被触发器" + trigger.getKey().getName() + CHAR_1
                + trigger.getKey().getGroup() + "触发了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        String logMsg = "(jobUnscheduled)" + triggerKey.getName() + CHAR_1 + triggerKey.getGroup() + "被移除了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        String logMsg = "(triggerFinalized)" + ZHUOYE + trigger.getJobKey().getName() + CHAR_1
                + trigger.getJobKey().getGroup() + ",触发器" + trigger.getKey().getName() + CHAR_1
                + trigger.getKey().getGroup() + "已经执行完成,后续将不会继续触发";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        if (StringUtils.isEmpty(triggerGroup)) {
            String logMsg = "(triggersPaused)" + "触发器组全部被暂停了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        } else {
            String logMsg = "(triggersPaused)" + "触发器组" + triggerGroup + "被暂停了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        }

    }

    @Override
    public void triggersResumed(String triggerGroup) {
        if (StringUtils.isEmpty(triggerGroup)) {
            String logMsg = "(triggersResumed)" + "触发器组全部被恢复了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        } else {
            String logMsg = "(triggersResumed)" + "触发器组" + triggerGroup + "被恢复了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        }
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        String logMsg = "(jobAdded)" + ZHUOYE + jobDetail.getKey().getName() + CHAR_1 + jobDetail.getKey().getGroup() + "被添加了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        String logMsg = "(jobDeleted)" + ZHUOYE + jobKey.getName() + CHAR_1 + jobKey.getGroup() + "被删除了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        String logMsg = "(jobPaused)" + ZHUOYE + jobKey.getName() + CHAR_1 + jobKey.getGroup() + "被暂停了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobsPaused(String jobGroup) {
        if (StringUtils.isEmpty(jobGroup)) {
            String logMsg = "(jobsPaused)" + "作业全部被暂停了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        } else {
            String logMsg = "(jobsPaused)" + "作业组" + jobGroup + "被暂停了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        }

    }

    @Override
    public void jobResumed(JobKey jobKey) {
        String logMsg = "(jobResumed)" + ZHUOYE + jobKey.getName() + CHAR_1 + jobKey.getGroup() + "被恢复了";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void jobsResumed(String jobGroup) {
        if (StringUtils.isEmpty(jobGroup)) {
            String logMsg = "(jobsResumed)" + "作业全部被恢复了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        } else {
            String logMsg = "(jobsResumed)" + "作业组" + jobGroup + "被恢复了";
            if (listenerLog != null) {
                listenerLog.save(logMsg);
            }
        }
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        // 获得异常详细信息
        String exceptionDetail = ExceptionUtils.getStackTrace(cause);
        String logMsg = "(schedulerError)" + "计划任务出错:" + msg + "\n" + exceptionDetail;
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulerInStandbyMode() {
        String logMsg = "(schedulerInStandbyMode)" + "计划任务为待机状态";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulerStarted() {
        String logMsg = "(schedulerStarted)" + "计划任务已经启动";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulerStarting() {
        String logMsg = "(schedulerStarting)" + "计划任务正在启动中";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulerShutdown() {
        String logMsg = "(schedulerShutdown)" + "计划任务已关闭";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulerShuttingdown() {
        String logMsg = "(schedulerShuttingdown)" + "计划任务正在关闭中";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }

    @Override
    public void schedulingDataCleared() {
        String logMsg = "(schedulingDataCleared)" + "计划任务数据被清除";
        if (listenerLog != null) {
            listenerLog.save(logMsg);
        }
    }
}
