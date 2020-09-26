package com.kancy.emailplus.core;

import com.kancy.emailplus.core.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * SpiMailPropertiesDataSource
 * 内部使用
 *
 * @author kancy
 * @date 2020/2/22 14:33
 */
class SpiEmailSenderPropertiesDataSource implements EmailSenderPropertiesDataSource {
    private static final Logger log = LoggerFactory.getLogger(SpiEmailSenderPropertiesDataSource.class);
    /**
     * 加载属性
     *
     * @return
     */
    @Override
    public List<EmailSenderProperties> load() {
        ServiceLoader<EmailSenderPropertiesDataSource> services = ServiceLoader.load(EmailSenderPropertiesDataSource.class, PooledEmailSender.class.getClassLoader());
        List<EmailSenderPropertiesDataSource> serviceList = new ArrayList<>();
        for (EmailSenderPropertiesDataSource service : services) {
            serviceList.add(service);
        }
        // 排序，按照order value 从大到小排序
        Collections.sort(serviceList, Comparator.comparingInt(o -> getOrderValue(o.getClass())));

        // 全部加载，并且去重
        Map<String, EmailSenderProperties> mailPropertiesMap = new TreeMap<>();
        for (EmailSenderPropertiesDataSource service : serviceList) {
            log.info("Load email properties from spi service : {}", service.getClass().getName());
            List<EmailSenderProperties> emailSenderPropertiesList = service.load();
            for (EmailSenderProperties properties : emailSenderPropertiesList) {
                if (mailPropertiesMap.containsKey(properties.getName())){
                    log.warn("EmailSender properties has repeated ： {}", properties.getName());
                }else{
                    mailPropertiesMap.put(properties.getName(), properties);
                }
            }
        }
        return new ArrayList<>(mailPropertiesMap.values());
    }

    /**
     * 获取类上的Order
     * @param clazz
     * @return
     */
    private int getOrderValue(Class<?> clazz) {
        Order annotation = clazz.getAnnotation(Order.class);
        if (Objects.nonNull(annotation)){
            return annotation.value();
        }
        return 0;
    }


}
