package com.jun.plugin.common.util;

import com.google.common.collect.Maps;
import com.jun.plugin.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    private PropertiesUtil() {}

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * PROP
     */
    private static final Properties PROP = new Properties();

    /**
     * 读取配置文件
     * @param fileName
     * @return void
     * @date 2018/8/31 17:29
     */
    public static void loadProps(String fileName) {
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream("" + fileName);
//            in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PROP.load(bf);
        } catch (IOException e) {
            logger.error("PropertiesUtil工具类读取配置文件出现IOException异常:{}", e.getMessage());
            throw new BusinessException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("PropertiesUtil工具类读取配置文件出现IOException异常:{}", e.getMessage());
                throw new BusinessException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
            }
        }
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return java.lang.String
     * @date 2018/8/31 17:29
     */
    public static String getProperty(String key){
        return PROP.getProperty(key);
    }
    public static Map getAllProperty(){
        Map map = new HashMap<>();
        for (final String name: PROP.stringPropertyNames()){
            map.put(name, PROP.getProperty(name));
        }
//        return Maps.fromProperties(PROP);
        return map;
    }
}
