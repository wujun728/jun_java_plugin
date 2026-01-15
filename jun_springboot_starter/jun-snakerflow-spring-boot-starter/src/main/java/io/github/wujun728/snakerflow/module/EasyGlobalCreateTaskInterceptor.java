
package io.github.wujun728.snakerflow.module;

import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.springframework.stereotype.Component;

/**
 * Easy Admin 全局拦截器（只拦截创建任务） 可用于任务到达时的短信提醒
 * 无需其他配置
 */
@Component
@Slf4j
public class EasyGlobalCreateTaskInterceptor implements SnakerInterceptor {
    /**
     * 拦截产生的任务对象，打印日志
     */
    @Override
    public void intercept(Execution execution) {
        for (Task task : execution.getTasks()) {
            StringBuffer buffer = new StringBuffer(100);
            buffer.append("创建任务[标识=").append(task.getId());
            buffer.append(",名称=").append(task.getDisplayName());
            buffer.append(",创建时间=").append(task.getCreateTime());
            buffer.append(",参与者={");
            if (task.getActorIds() != null) {
                for (String actor : task.getActorIds()) {
                    buffer.append(actor).append(";");
                }
            }
            buffer.append("}]");
            log.info(buffer.toString());
        }
    }
}
