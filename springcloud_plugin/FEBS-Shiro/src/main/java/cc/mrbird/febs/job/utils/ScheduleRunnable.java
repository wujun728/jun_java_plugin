package cc.mrbird.febs.job.utils;

import cc.mrbird.febs.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 *
 * @author MrBird
 */
@Slf4j
public class ScheduleRunnable implements Runnable {

    private final Object target;
    private final Method method;
    private final String params;

    ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        target = SpringContextUtil.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            log.error("执行定时任务失败", e);
        }
    }

}
