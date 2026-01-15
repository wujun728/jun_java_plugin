package io.github.wujun728.snakerflow.module;

import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.Completion;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.springframework.stereotype.Component;

/**
 * 任务、实例完成时触发动作的接口
 */
@Component
@Slf4j
public class GeneralCompletion implements Completion {

    @Override
    public void complete(HistoryTask task) {

        log.info("The task[{}] has been user[{}] has completed", task, task.getOperator());
    }

    @Override
    public void complete(HistoryOrder order) {


        log.info("The order[{}] has completed", order);
    }
}