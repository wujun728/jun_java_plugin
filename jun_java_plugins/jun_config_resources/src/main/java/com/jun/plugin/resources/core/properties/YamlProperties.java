package com.jun.plugin.resources.core.properties;

import org.yaml.snakeyaml.Yaml;

import com.jun.plugin.resources.encrypt.ResourceEncrypt;
import com.jun.plugin.resources.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Yaml解析器；目前只支持纯key value模式
 * Created by Hong on 2017/12/27.
 */
public class YamlProperties extends Hashtable<Object, Object> {

    private final static String ANNOTATION = "#";

    /**
     * 创建没有默认资源的空资源
     */
    public YamlProperties() {
        super();
    }

    /***
     * 创建有默认资源的非空资源
     * @param defaults  默认
     */
    public YamlProperties(Properties defaults) {
        super(defaults);
    }

    public synchronized Object setProperty(String key, String value) {
        return super.put(key, value);
    }

    public synchronized void load(InputStream inStream) throws IOException {
        Yaml yaml = new Yaml();
        Map<Object, Object> kvs = yaml.load(inStream);
        getValue("", kvs);
    }

    /**
     * 获取Key Value
     *
     * @param key 新Key
     * @param map key对象的集合
     */
    private void getValue(String key, Map<Object, Object> map) {
        if (map == null) {
            return;
        }
        Iterator<Object> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String text = key;
            Object keys = iterator.next();
            if (StringUtils.isNotEmpty(text)) {
                text += ".";
            }
            text += keys;
            Object value = map.get(keys);
            if (value instanceof Map) {
                getValue(text, (Map<Object, Object>) value);
            } else {
                if (value != null) {
                    super.put(text, ResourceEncrypt.value(value));
                }
            }
        }
    }

}
