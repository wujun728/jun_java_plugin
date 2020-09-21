package org.itkk.udf.scheduler.impl;

import lombok.extern.slf4j.Slf4j;
import org.itkk.udf.scheduler.IListenerEvent;
import org.itkk.udf.scheduler.domain.ScheduledTriggerLog;

/**
 * DefListenerEvent
 */
@Slf4j
public class DefListenerEvent implements IListenerEvent {

    @Override
    public void save(ScheduledTriggerLog scheduledTriggerLog) {
        log.info("----- DefListenerEvent.ScheduledTriggerLog -----> {}", scheduledTriggerLog.toString());
    }

    @Override
    public void delete(String fireInstanceId) {
        log.info("----- DefListenerEvent.delete -----> {}", fireInstanceId);
    }

    @Override
    public void save(String saveLog) {
        log.info("----- DefListenerEvent.save -----> {}", saveLog);
    }

    @Override
    public void clearScheduledTriggerLog() {
        log.info("----- DefListenerEvent.clearScheduledTriggerLog ----->");
    }

    @Override
    public void clearScheduledLog() {
        log.info("----- DefListenerEvent.clearScheduledLog ----->");
    }

}
