
package io.github.wujun728.snakerflow.module;

import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;

/**
 * Easy Admin 局部拦截器，这里需要配置在 xml中，postInterceptors = com.laker.admin.framework.ext.snakerflow.EasyPartEndProcessInterceptor
 * 这里以流程结束拦截为例，例如配置 某些流程结束 短信通知报告发起人
 * 不需要注解了，反射实例化的
 */
@Slf4j
public class EasyPartEndProcessInterceptor implements SnakerInterceptor {
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
