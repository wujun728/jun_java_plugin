/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
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

import redis.clients.jedis.BinaryJedisCommands;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Redis 缓存操作封装，基于 Hashs 实现多个 Region 的缓存
 * @author Wujun
 *
 * 重要提示！！！  hash 存储模式无法单独对 key 设置 expire
 */
public class RedisCache {

    private String namespace;
    private RedisClient client;

    /**
     * 缓存构造
     * @param namespace 命名空间，用于在多个实例中避免 key 的重叠
     * @param client 缓存客户端接口
     */
    public RedisCache(String namespace, RedisClient client) {
        this.client = client;
        this.namespace = namespace;
    }

    /**
     * 在region里增加一个可选的层级,作为命名空间,使结构更加清晰
     * 同时满足小型应用,多个J2Cache共享一个redis database的场景
     *
     * @param session_id
     * @return
     */
    private String getRegionName(String session_id) {
        if (namespace != null && !namespace.isEmpty())
            session_id = namespace + ":" + session_id;
        return session_id;
    }

    public byte[] getBytes(String session_id, String key) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            return client.get().hget(regionBytes, key.getBytes());
        } finally {
            client.release();
        }
    }

    public List<byte[]> getBytes(String session_id, Collection<String> keys) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            byte[][] bytes = keys.stream().map(k -> k.getBytes()).toArray(byte[][]::new);
            return client.get().hmget(regionBytes, bytes);
        } finally {
            client.release();
        }
    }

    public void setBytes(String session_id, String key, byte[] bytes) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            client.get().hset(regionBytes, key.getBytes(), bytes);
        } finally {
            client.release();
        }
    }

    public void setBytes(String session_id, Map<String,byte[]> bytes, int expireInSeconds) {
        try {
            Map<byte[], byte[]> data = new HashMap<>();
            bytes.forEach((k,v) -> data.put(k.getBytes(), v));
            byte[] regionBytes = getRegionName(session_id).getBytes();
            BinaryJedisCommands jedis = client.get();
            jedis.hmset(regionBytes, data);
            jedis.expire(regionBytes, expireInSeconds);
        } finally {
            client.release();
        }
    }

    public boolean exists(String session_id) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            return client.get().exists(regionBytes);
        } finally {
            client.release();
        }
    }

    public boolean exists(String session_id, String key) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            return client.get().hexists(regionBytes, key.getBytes());
        } finally {
            client.release();
        }
    }

    public void evict(String session_id, String...keys) {
        if (keys == null || keys.length == 0)
            return;
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            byte[][] bytes = Arrays.stream(keys).map(k -> k.getBytes()).toArray(byte[][]::new);
            client.get().hdel(regionBytes, bytes);
        } finally {
            client.release();
        }
    }

    public long ttl(String session_id, int ttl) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            return client.get().expire(regionBytes, ttl);
        } finally {
            client.release();
        }
    }

    public List<String> keys(String session_id) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            return client.get().hkeys(regionBytes).stream().map(bs -> new String(bs)).collect(Collectors.toList());
        } finally {
            client.release();
        }
    }

    public void clear(String session_id) {
        try {
            byte[] regionBytes = getRegionName(session_id).getBytes();
            client.get().del(regionBytes);
        } finally {
            client.release();
        }
    }

    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
        }
    }

}
