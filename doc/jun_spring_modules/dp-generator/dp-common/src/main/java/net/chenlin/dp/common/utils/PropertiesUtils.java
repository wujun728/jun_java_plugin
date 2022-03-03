package net.chenlin.dp.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源文件读取工具
 * @author ZhouChenglin
 * @date 2017/12/14
 */
public class PropertiesUtils {

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesUtils> configMap = new HashMap<String, PropertiesUtils>(16);

    /**
     * 打开文件时间，判断超时使用
     */
    private Date loadTime = null;

    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle = null;

    /**
     * 默认资源文件名称
     */
    private static final String NAME = "config";

    /**
     * 缓存时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 私有构造方法，创建单例
     * @param name
     */
    private PropertiesUtils(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesUtils getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesUtils getInstance(String name) {
        PropertiesUtils conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesUtils(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesUtils(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    /**
     * 根据key读取value
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     * 根据key读取value(整型)
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(布尔值)
     * @param key
     * @return
     */
    public boolean getBool(String key) {
        String flag = "true";
        try {
            String value = resourceBundle.getString(key);
            if (flag.equals(value)) {
                return true;
            }
            return false;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }

}
