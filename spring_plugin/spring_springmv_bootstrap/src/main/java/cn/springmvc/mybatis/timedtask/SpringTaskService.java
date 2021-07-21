package cn.springmvc.mybatis.timedtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.springmvc.mybatis.common.utils.DateUtil;

/**
 * @author Vincent.wang
 *
 */
@Component
public class SpringTaskService {

    private static final Logger log = LoggerFactory.getLogger("cn.springmvc.mybatis.service");

    @Scheduled(cron = "0 0/5 * * * ?")
    public void task() {
        String dateString = DateUtil.dateToString(null, DateUtil.fm_yyyy_MM_dd_HHmmssSSS);
        log.trace("## tract {}", dateString);
        log.debug("## debug {}", dateString);
        log.info("## info {}", dateString);
        log.warn("## warn {}", dateString);
        log.error("## error {}", dateString);
    }

}
