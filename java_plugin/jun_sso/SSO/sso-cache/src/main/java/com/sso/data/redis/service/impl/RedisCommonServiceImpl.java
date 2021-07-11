package com.sso.data.redis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sso.data.redis.service.CommonSortQuery;
import com.sso.data.redis.service.RedisCommonService;

public class RedisCommonServiceImpl implements RedisCommonService {
    private static final String ID = "id";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations;

    @Override
    public JSONObject save(String keySpace, JSONObject data) {
        String id = data.getString(ID);
        if (StringUtils.isEmpty(id)) {
            id = randomId();
            setOperations.add(keySpace, id);
            data.put(ID, id);
            hashOperations.putAll(combineKey(keySpace, id), data);
        } else {
            hashOperations.putAll(combineKey(keySpace, id), data);
        }
        return data;
    }

    @Override
    public JSONObject get(String keySpace, String id) {
        String combineKey = combineKey(keySpace, id);
        if (redisTemplate.hasKey(combineKey)) {
            Map<String, Object> result = hashOperations.entries(combineKey);
            return JSONObject.parseObject(JSONObject.toJSONString(result));
        }
        return null;
    }

    @Override
    public void delete(String keySpace, String id) {
        setOperations.remove(keySpace, id);
        redisTemplate.delete(combineKey(keySpace, id));
    }

    @Override
    public JSONArray findAll(final String keySpace) {
        final Set<String> ids = setOperations.members(keySpace);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }

        List<Object> dataList = redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                for (String id : ids) {
                    connection.hGetAll(keySerializer.serialize(combineKey(keySpace, id)));
                }
                return null;
            }
        });

        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }

        JSONArray results = new JSONArray();
        for (Object data : dataList) {
            results.add(JSONObject.parseObject(JSONObject.toJSONString(data)));
        }

        return results;
    }

    @Override
    public Page findAll(final String keySpace, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl(this.findAll(keySpace));
        }

        final String property = pageable.getSort() == null ? null : pageable.getSort().iterator().next().getProperty();
        final String direction = pageable.getSort() == null ? null : pageable.getSort().iterator().next().getDirection().toString();

        long total = setOperations.size(keySpace);
        final List<Object> ids = redisTemplate.sort(new CommonSortQuery(keySpace, pageable.getOffset(),
                pageable.getPageSize(), property, direction));

        List<Object> dataList = redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                for (Object id : ids) {
                    connection.hGetAll(keySerializer.serialize(combineKey(keySpace, (String) id)));
                }
                return null;
            }
        });

        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }

        JSONArray results = new JSONArray();
        for (Object data : dataList) {
            results.add(JSONObject.parseObject(JSONObject.toJSONString(data)));
        }

        return new PageImpl(results, pageable, total);
    }

    private static String randomId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static String combineKey(String keySpace, String id) {
        return keySpace + ":" + id;
    }
}
