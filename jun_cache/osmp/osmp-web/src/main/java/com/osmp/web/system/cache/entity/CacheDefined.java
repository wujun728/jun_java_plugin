package com.osmp.web.system.cache.entity;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author wangqiang
 *
 */
public class CacheDefined implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1363901681565606163L;

    // 方法的唯一标示，由方法全路径加参数类型组成
    private String id;

    // 缓存名称
    private String name;

    // 方法
    private Method method;

    // 前缀
    private String prefix;

    // 有效时间 秒
    private int timeToLive;

    // 空闲时长 秒
    private int timeToIdle;

    // 缓存状态
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getTimeToIdle() {
        return timeToIdle;
    }

    public void setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CacheDefined [id=" + id + ", name=" + name + ", method=" + method + ", prefix=" + prefix
                + ", timeToLive=" + timeToLive + ", timeToIdle=" + timeToIdle + ", state=" + state + "]";
    }

}
