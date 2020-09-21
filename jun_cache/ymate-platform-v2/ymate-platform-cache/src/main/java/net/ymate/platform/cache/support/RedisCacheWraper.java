/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.cache.support;

import net.ymate.platform.cache.*;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.redis.IRedis;
import net.ymate.platform.persistence.redis.IRedisCommandsHolder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/7 上午12:16
 * @version 1.0
 */
public class RedisCacheWraper implements ICache {

    private String __cacheName;

    private IRedis __redis;

    private ICaches __owner;

    public RedisCacheWraper(ICaches owner, IRedis redis, String cacheName, final ICacheEventListener listener) {
        __owner = owner;
        __redis = redis;
        __cacheName = cacheName;
        //
        if (listener != null) {
            IRedisCommandsHolder _holder = null;
            try {
                _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
                Jedis _jedis = _holder.getJedis();
                if (_jedis != null) {
                    _jedis.subscribe(new JedisPubSub() {
                        public void onMessage(String channel, String message) {
                            if (StringUtils.isNotBlank(message)) {
                                listener.notifyElementExpired(__cacheName, message);
                            }
                        }
                    }, "__keyevent@" + _holder.getDataSourceCfgMeta().getMasterServerMeta().getDatabase() + "__:expired");
                }
            } catch (Exception e) {
                throw new CacheException(RuntimeUtils.unwrapThrow(e));
            } finally {
                if (_holder != null) {
                    _holder.release();
                }
            }
        }
    }

    protected String __doSerializeKey(Object key) throws Exception {
        if (key instanceof String || key instanceof StringBuilder || key instanceof StringBuffer || key instanceof Number) {
            return key.toString();
        }
        return DigestUtils.md5Hex(("" + key).getBytes());
    }

    protected String __doSerializeValue(Object value) throws Exception {
        return Base64.encodeBase64String(__owner.getModuleCfg().getSerializer().serialize(value));
    }

    protected Object __doUnserializeValue(String value) throws Exception {
        if (value == null) {
            return null;
        }
        byte[] _bytes = Base64.decodeBase64(value);
        return __owner.getModuleCfg().getSerializer().deserialize(_bytes);
    }

    public Object get(Object key) throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            return __doUnserializeValue(_holder.getCommands().hget(__cacheName, __doSerializeKey(key)));
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void put(Object key, Object value) throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            _holder.getCommands().hset(__cacheName, __doSerializeKey(key), __doSerializeValue(value));
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void update(Object key, Object value) throws CacheException {
        put(key, value);
    }

    public List<String> keys() throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            return new ArrayList<String>(_holder.getCommands().hkeys(__cacheName));
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void remove(Object key) throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            _holder.getCommands().hdel(__cacheName, __doSerializeKey(key));
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void removeAll(Collection<?> keys) throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            List<String> _keys = new ArrayList<String>(keys.size());
            for (Object _key : keys) {
                _keys.add(__doSerializeKey(_key));
            }
            _holder.getCommands().hdel(__cacheName, _keys.toArray(new String[_keys.size()]));
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void clear() throws CacheException {
        IRedisCommandsHolder _holder = null;
        try {
            _holder = __redis.getDefaultDataSourceAdapter().getCommandsHolder();
            _holder.getCommands().del(__cacheName);
        } catch (Exception e) {
            throw new CacheException(RuntimeUtils.unwrapThrow(e));
        } finally {
            if (_holder != null) {
                _holder.release();
            }
        }
    }

    public void destroy() throws CacheException {
        __redis = null;
    }

    public ICacheLocker acquireCacheLocker() {
        return null;
    }
}
