package org.unique.plugin.image.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
/**
 * json工具类
 * @author rex
 *
 */
@SuppressWarnings("unchecked")
public class JSONUtil {

    public static <T> String toJSON(T t) {
        if (null != t) {
            return JSON.toJSONString(t);
        }
        return null;
    }

    /**
     * map转json
     * 
     * @author：rex
     * @param map
     * @return
     */
    public static <K, V> String map2Json(Map<K, V> map) {
        if(null != map && !map.isEmpty()){
            return JSON.toJSONString(map);
        }
        return null;
    }

    /**
     * list转json
     * 
     * @author：rex
     * @param list
     * @return
     */
    public static <T> String list2JSON(List<T> list) {
        if(null != list && !list.isEmpty()){
            return JSON.toJSONString(list);
        }
        return null;
    }

    /**
     * JSON转map
     * 
     * @param <K>
     * @author：rex
     * @param json
     * @return
     */
    public static <K, V> Map<K, V> json2Map(final String json) {
        if (null != json && !json.equals("")) {
            return JSON.parseObject(json, HashMap.class);
        }
        return null;
    }
    
    /**
     * json转对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T json2Object(final String json, Class<T> clazz) {
        if (null != json && !"".equals(json) && null != clazz) {
            return JSON.parseObject(json, clazz);
        }
        return null;
    }
    
}
