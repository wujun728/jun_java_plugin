/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.jun.plugin.picturemanage.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;


/**
 * Map工具类
 *
 * @author EddyChen
 */
public class CustomMap extends HashMap<String, Object> {


    public static CustomMap create(String key, Object val) {
        return new CustomMap().put(key, val);
    }

    public static CustomMap create() {
        return new CustomMap();
    }


    @Override
    public CustomMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }


    public CustomMap removeReturnThis(String key) {
        super.remove(key);
        return this;
    }


    public String toJson() {
        return JSONObject.toJSONString(this);
    }

    public String getToString(String key) {
        return MapUtils.getString(this, key);
    }

    /**
     * 根据map中的某个key 去除List中重复的map
     *
     * @param list
     * @param mapKey
     * @return
     * @author shijing
     */
    public static List<Map<String, Object>> removeRepeatMapByKey(List<Map<String, Object>>
                                                                         list, String mapKey) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Map> msp = new HashMap<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Map map = list.get(i);
            String id = MapUtils.getString(map, mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }
        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }
}
