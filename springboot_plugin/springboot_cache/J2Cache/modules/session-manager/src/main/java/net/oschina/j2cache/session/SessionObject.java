/**
 * Copyright (c) 2015-2018, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session 会话对象
 * @author Wujun
 */
public class SessionObject implements Serializable {

    public static final String KEY_CREATE_AT = "CREATED_AT";
    public static final String KEY_ACCESS_AT = "ACCESS_AT" ;

    private String id;
    private long created_at;
    private long access_at;
    private int maxInactiveInterval;
    private ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    public SessionObject(){}

    public SessionObject(String session_id, List<String> keys, List<byte[]> datas) throws IOException, ClassNotFoundException {
        this.id = session_id;
        for(int i=0;i<keys.size();i++) {
            String key = keys.get(i);
            if(KEY_CREATE_AT.equals(key))
                this.created_at = Long.parseLong(new String(datas.get(i)));
            else if(KEY_ACCESS_AT.equals(key))
                this.access_at = Long.parseLong(new String(datas.get(i)));
            else {
                attributes.put(key, Serializer.read(datas.get(i)));
            }
        }
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public boolean containsKey(String key) {
        return attributes.containsKey(key);
    }

    public Object put(String key, Object value) {
        if (value == null) {
            // 当值为 null 时 当remove  处理 避免 ConcurrentHashMap 抛异常
            return remove(key);
        } else {
            return attributes.put(key, value);
        }
    }

    public Object remove(String key) {
        return attributes.remove(key);
    }

    public void clear() {
        attributes.clear();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
        this.access_at = created_at;
    }

    public long getLastAccess_at() {
        return access_at;
    }

    public void setLastAccess_at(long access_at) {
        this.access_at = access_at;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public ConcurrentHashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }
}
