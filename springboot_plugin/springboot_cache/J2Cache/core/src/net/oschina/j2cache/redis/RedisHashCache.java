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
package net.oschina.j2cache.redis;

import net.oschina.j2cache.Level2Cache;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Redis 缓存操作封装，基于 Hashs 实现多个 Region 的缓存
 * @author Wujun
 * @author Wujun
 *
 * 重要提示！！！  hash 存储模式无法单独对 key 设置 expire
 */
public class RedisHashCache implements Level2Cache {

    private String namespace;
    private byte[] regionBytes;
    private RedisClient client;

    /**
     * 缓存构造
     * @param namespace 命名空间，用于在多个实例中避免 key 的重叠
     * @param region 缓存区域的名称
     * @param client 缓存客户端接口
     */
    public RedisHashCache(String namespace, String region, RedisClient client) {
        if (region == null || region.trim().isEmpty())
            region = "_"; // 缺省region

        this.client = client;
        this.namespace = namespace;
        this.regionBytes = getRegionName(region).getBytes();
    }

    /**
     * 在region里增加一个可选的层级,作为命名空间,使结构更加清晰
     * 同时满足小型应用,多个J2Cache共享一个redis database的场景
     *
     * @param region
     * @return
     */
    private String getRegionName(String region) {
        if (namespace != null && !namespace.isEmpty())
            region = namespace + ":" + region;
        return region;
    }

    @Override
    public byte[] getBytes(String key) {
        try {
            return client.get().hget(regionBytes, key.getBytes());
        } finally {
            client.release();
        }
    }

    @Override
    public List<byte[]> getBytes(Collection<String> keys) {
        try {
            byte[][] bytes = keys.stream().map(k -> k.getBytes()).toArray(byte[][]::new);
            return client.get().hmget(regionBytes, bytes);
        } finally {
            client.release();
        }
    }

    @Override
    public void setBytes(String key, byte[] bytes) {
        try {
            client.get().hset(regionBytes, key.getBytes(), bytes);
        } finally {
            client.release();
        }
    }

    @Override
    public void setBytes(Map<String,byte[]> bytes) {
        try {
            Map<byte[], byte[]> data = new HashMap<>();
            bytes.forEach((k,v) -> data.put(k.getBytes(), v));
            client.get().hmset(regionBytes, data);
        } finally {
            client.release();
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return client.get().hexists(regionBytes, key.getBytes());
        } finally {
            client.release();
        }
    }

    @Override
    public void evict(String...keys) {
        if (keys == null || keys.length == 0)
            return;
        try {
            byte[][] bytes = Arrays.stream(keys).map(k -> k.getBytes()).toArray(byte[][]::new);
            client.get().hdel(regionBytes, bytes);
        } finally {
            client.release();
        }
    }

    @Override
    public Collection<String> keys() {
        try {
            return client.get().hkeys(regionBytes).stream().map(bs -> new String(bs)).collect(Collectors.toList());
        } finally {
            client.release();
        }
    }

    @Override
    public void clear() {
        try {
            client.get().del(regionBytes);
        } finally {
            client.release();
        }
    }

}
