package com.sso.data.redis.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RedisCommonService {

    JSONObject save(String keySpace, JSONObject data);

    JSONObject get(String keySpace, String id);

    void delete(String keySpace, String id);

    JSONArray findAll(String keySpace);

    Page findAll(String keySpace, Pageable pageable);
}
