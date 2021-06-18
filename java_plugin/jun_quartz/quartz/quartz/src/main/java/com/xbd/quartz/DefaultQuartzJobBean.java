package com.xbd.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xbd.quartz.task.AbstractQuartzTask;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p>
 * 默认定时任务QuartzJobBean
 * </p>
 *
 * @author 小不点
 */
public class DefaultQuartzJobBean extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static String JOBDETAIL_KEY_TARGETCLASS = "targetClass";
    public final static String JOBDETAIL_KEY_TARGETOBJECT = "targetObject";
    public final static String JOBDETAIL_KEY_TARGETMETHOD = "targetMethod";
    public final static String JOBDETAIL_KEY_TARGETMETHOD_PARAM = "targetMethodParam";
    public final static String JOBDETAIL_VALUE_TARGETMETHOD = AbstractQuartzTask.TARGETMETHOD_DEFAULT;

    private String targetClass;
    private String targetObject;
    private String targetMethod;
    private String targetMethodParam;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            if (StringUtils.isBlank(targetClass) && StringUtils.isBlank(targetObject)) {
                throw new JobExecutionException("定时任务目标对象为空！");
            }

            if (StringUtils.isBlank(targetMethod)) {
                throw new JobExecutionException("定时任务目标方法为空！");
            }

            Object bean;
            Method m;

            if (StringUtils.isNotBlank(targetObject)) {
                ApplicationContext ctx = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");

                bean = ctx.getBean(targetObject);
            } else {
                bean = Class.forName(targetClass).newInstance();
            }

            if (StringUtils.isBlank(targetMethodParam)) {
                m = bean.getClass().getMethod(targetMethod);

                m.invoke(bean, new Object[]{});
            } else {
                Object paramObject = JSON.parse(targetMethodParam);

                if (paramObject instanceof JSONObject) {
                    m = bean.getClass().getMethod(targetMethod, Object.class);

                    m.invoke(bean, new Object[]{targetMethodParam});
                } else if (paramObject instanceof JSONArray) {
                    JSONArray params = (JSONArray) paramObject;

                    List<Class> argClasss = new ArrayList<>();
                    List<Object> args = new ArrayList<>();

                    for (Object param : params) {
                        argClasss.add(param.getClass());
                        args.add(param);
                    }

                    m = bean.getClass().getMethod(targetMethod, argClasss.toArray(new Class[]{}));

                    m.invoke(bean, ((JSONArray) paramObject).toArray(new Object[]{}));
                }
            }
        } catch (JobExecutionException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | SchedulerException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new JobExecutionException(e.getMessage(), e);
        }
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getTargetMethodParam() {
        return targetMethodParam;
    }

    public void setTargetMethodParam(String targetMethodParam) {
        this.targetMethodParam = targetMethodParam;
    }

}
