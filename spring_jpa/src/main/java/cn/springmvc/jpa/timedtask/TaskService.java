package cn.springmvc.jpa.timedtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.springmvc.jpa.common.utils.date.DateUtil;

/**
 * @author Vincent.wang
 *
 */
@Component
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void task() {
        String dateString = DateUtil.dateToString(null, DateUtil.fm_yyyy_MM_dd_HHmmssSSS);
        log.trace("## tract {}", dateString);
        log.debug("## debug {}", dateString);
        log.info("## info {}", dateString);
        log.warn("## warn {}", dateString);
        log.error("## error {}", dateString);
    }

}
