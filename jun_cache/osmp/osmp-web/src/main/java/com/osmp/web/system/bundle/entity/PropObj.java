package com.osmp.web.system.bundle.entity;

import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;

/**
 * Description:
 *
 * @author: wangqiang
 * @date: 2014年11月25日 下午2:19:44
 */
/**
 * @author wangqiang
 *
 */
public class PropObj {
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        StringBuilder sb = new StringBuilder();
        if (value != null && JSONArray.class.isAssignableFrom(value.getClass())) {
            JSONArray arr = (JSONArray) value;
            Iterator<Object> iterator = arr.iterator();
            boolean hasNext = iterator.hasNext();
            while (hasNext) {
                sb.append(iterator.next());
                hasNext = iterator.hasNext();
                if (hasNext) {
                    sb.append("<br/>");
                }
            }
        } else {
            sb.append(value == null ? "" : value);
        }

        return sb.toString();
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropObj [key=" + key + ", value=" + value + "]";
    }

}
