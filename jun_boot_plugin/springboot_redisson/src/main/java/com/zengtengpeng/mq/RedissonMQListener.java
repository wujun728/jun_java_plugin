package com.zengtengpeng.mq;

import com.zengtengpeng.annotation.MQListener;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;


/**
 * mq监听
 */
public class RedissonMQListener implements BeanPostProcessor {


    Logger logger = LoggerFactory.getLogger(RedissonMQListener.class);
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            MQListener annotation = AnnotationUtils.findAnnotation(method, MQListener.class);
            if(annotation!=null){
                switch (annotation.model()){
                    case PRECISE:
                        RTopic topic = redissonClient.getTopic(annotation.name());
                        logger.info("注解redisson精准监听器name={}",annotation.name());
                        topic.addListener(Object.class, (channel, msg) -> {
                            try {
                                Object[] aras=new Object[method.getParameterTypes().length];
                                int index=0;
                                for (Class parameterType : method.getParameterTypes()) {
                                    String simpleName = parameterType.getSimpleName();
                                    if("CharSequence".equals(simpleName)){
                                        aras[index++]=channel;
                                    }else if (msg.getClass().getSimpleName().equals(simpleName)||"Object".equals(simpleName)){
                                        aras[index++]=msg;
                                    }else {
                                        aras[index++]=null;
                                    }
                                }
                                method.invoke(bean,aras);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case PATTERN:
                        RPatternTopic patternTopic = redissonClient.getPatternTopic(annotation.name());
                        logger.info("注解redisson模糊监听器name={}",annotation.name());
                        patternTopic.addListener(Object.class, (pattern, channel, msg) -> {
                            try {
                                Object[] aras=new Object[method.getParameterTypes().length];
                                int index=0;
                                boolean patternFlag = false;
                                for (Class parameterType : method.getParameterTypes()) {
                                    String simpleName = parameterType.getSimpleName();
                                    if("CharSequence".equals(simpleName)){
                                        if(!patternFlag){
                                            patternFlag=true;
                                            aras[index++]=pattern;
                                        }else {
                                            aras[index++]=channel;
                                        }
                                    }else if (msg.getClass().getSimpleName().equals(simpleName)||"Object".equals(simpleName)){
                                        aras[index++]=msg;
                                    }else {
                                        aras[index++]=null;
                                    }
                                }
                                method.invoke(bean,aras);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;

                }

            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }
}
