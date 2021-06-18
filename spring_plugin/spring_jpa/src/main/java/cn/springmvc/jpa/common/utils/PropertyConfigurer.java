package cn.springmvc.jpa.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Property工具类
 * 
 * @author Vincent.wang
 *
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object prop : props.keySet()) {
            String key = prop.toString();
            ctxPropertiesMap.put(key, props.getProperty(key));
        }
    }

    public static String getValue(String name) {
        Object value = ctxPropertiesMap.get(name);
        if (null != value)
            return value.toString();
        else
            return "";
    }

    public static String[] getValues(String name) {
        Object value = ctxPropertiesMap.get(name);
        if (null != value)
            return StringUtils.split(value.toString(), ",");
        else
            return null;
    }

}