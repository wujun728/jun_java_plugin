package com.jun.plugin.resources.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.resources.Constants;
import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.convert.impl.*;
import com.jun.plugin.resources.core.AutoResources;
import com.jun.plugin.resources.encrypt.ResourceEncrypt;
import com.jun.plugin.resources.utils.ResourcesUtils;
import com.jun.plugin.resources.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Hong on 2017/12/27.
 */
public class GlobalConfig extends AbstractConfig implements Config, RefreshConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalConfig.class);

    private static final Map<Object, Object> KVS = new Hashtable<>();

    {
        /**
         * 初始化，加载默认的配置文件
         */
        try {
            KVS.clear();
            //读取默认配置
            Resources resources = super.getResources(Constants.INIT_CONFIG);
            if (resources != null && resources.getResources() != null) {
                this.putAll(resources.getResources());
            }
            //读取多环境配置
            resources = super.getResources(ResourcesUtils.getProfileProperties(Constants.INIT_CONFIG, System.getProperty(KeyConstants.PROFILE)));
            if (resources != null && resources.getResources() != null) {
                this.putAll(resources.getResources());
            }
            //将系统属性加载进CONFIG中
            this.putAll(System.getProperties());
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Init Config Fail.");
            }
        }
    }

    /**
     * 设置密钥Key
     *
     * @param key 密钥
     */
    public static void useKey(String key) {
        ResourceEncrypt.use(key);
    }

    /**
     * 单例钩子
     */
    private static class SingletonHolder {
        private static GlobalConfig INSTANCE = new GlobalConfig();
    }

    /**
     * 获取GlobalConfig实例
     *
     * @return
     */
    private static GlobalConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取单例GlobalConfig
     *
     * @return Config
     */
    public static Config get() {
        return getInstance();
    }

    /**
     * 读取配置资源集合
     *
     * @param var1 资源文件名集合
     * @return Config
     */
    public static Config readConfig(String[] var1) {
        getInstance().read(var1);
        return getInstance();
    }

    /**
     * 添加配置
     *
     * @param config 配置集合
     * @return Config
     */
    public static Config putAll(Map<Object, Object> config) {
        KVS.putAll(config);
        return getInstance();
    }

    /**
     * 添加配置
     *
     * @param key   Key
     * @param value Value
     * @return Config
     */
    public static Config put(Object key, Object value) {
        KVS.put(key, value);
        return getInstance();
    }

    /**
     * 封闭的构造函数
     */
    private GlobalConfig() {

    }

    /**
     * 读取配置资源
     *
     * @param var1 资源名
     */
    private void read(String[] var1) {
        if (var1 == null) {
            return;
        }
        for (String name : var1) {
            this.read(name);
        }
    }

    /**
     * 读取配置资源
     *
     * @param name 资源名
     */
    private void read(String name) {
        try {
            if (StringUtils.isEmpty(name)) {
                return;
            }
            Resources resources = new AutoResources(name.trim());
            KVS.putAll(resources.getResources());
        } catch (IOException e) {
        }
    }

    @Override
    public String getValue(String key) {
        return new StringConvert().convert(KVS.get(key));
    }

    @Override
    public Boolean getBooleanValue(String key) {
        return new BooleanConvert().convert(KVS.get(key));
    }

    @Override
    public Integer getIntegerValue(String key) {
        return new IntegerConvert().convert(KVS.get(key));
    }

    @Override
    public Float getFloatValue(String key) {
        return new FloatConvert().convert(KVS.get(key));
    }

    @Override
    public Double getDoubleValue(String key) {
        return new DoubleConvert().convert(KVS.get(key));
    }

    @Override
    public Character getCharValue(String key) {
        return new CharConvert().convert(KVS.get(key));
    }

    @Override
    public Object get(String key) {
        return KVS.get(key);
    }

    @Override
    public Map<Object, Object> getConfig() {
        return new HashMap<>(KVS);
    }

    @Override
    public void clear() {
        KVS.clear();
    }

    @Override
    public void refresh() {
        SingletonHolder.INSTANCE = new GlobalConfig();
    }
}

