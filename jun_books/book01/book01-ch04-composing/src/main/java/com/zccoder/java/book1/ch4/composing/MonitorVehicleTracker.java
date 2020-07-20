package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 标题：基于监视器的机动车追踪器实现<br>
 * 描述：<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = this.deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return this.deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint location = locations.get(id);
        return location == null ? null : new MutablePoint(location);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint location = locations.get(id);
        if (location == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        location.x = x;
        location.y = y;
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> param) {
        Map<String, MutablePoint> result = new HashMap<>(16);
        for (String id : param.keySet()) {
            result.put(id, new MutablePoint(param.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }

}
