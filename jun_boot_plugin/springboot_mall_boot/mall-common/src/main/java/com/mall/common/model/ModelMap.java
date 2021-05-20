package com.mall.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 封装map 类型转换
 * @author Wujun
 * @version 1.0
 * @create 2018/11/8 11:26
 **/
public class ModelMap extends HashMap {

    public String getStr(String attr) {
        Object s = get(attr);
        return s != null ? s.toString() : null;
    }

    public ModelMap put(Object key, Object vale) {
        super.put(key, vale);
        return this;
    }



    public Integer getInt(String attr) {
        Number n = (Number)get(attr);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(String attr) {
        Number n = (Number)get(attr);
        return n != null ? n.longValue() : null;
    }


    public java.math.BigInteger getBigInteger(String attr) {
        return (java.math.BigInteger)get(attr);
    }


    public java.util.Date getDate(String attr) {
        return (java.util.Date)get(attr);
    }


    public java.sql.Time getTime(String attr) {
        return (java.sql.Time)get(attr);
    }


    public java.sql.Timestamp getTimestamp(String attr) {
        return (java.sql.Timestamp)get(attr);
    }


    public Double getDouble(String attr) {
        Number n = (Number)get(attr);
        return n != null ? n.doubleValue() : null;
    }


    public Float getFloat(String attr) {
        Number n = (Number)get(attr);
        return n != null ? n.floatValue() : null;
    }

    public Short getShort(String attr) {
        Number n = (Number)get(attr);
        return n != null ? n.shortValue() : null;
    }


    public Boolean getBoolean(String attr) {
        return (Boolean)get(attr);
    }


    public java.math.BigDecimal getBigDecimal(String attr) {
        return (java.math.BigDecimal)get(attr);
    }


    public byte[] getBytes(String attr) {
        return (byte[])get(attr);
    }

    public <T> List<T> getList(String attr) {
        Object object = get(attr);
        return object == null ? null : (List<T>) get(attr);
    }

    public <T> Set<T> getSet(String attr) {
        return (Set<T>) get(attr);
    }
}
