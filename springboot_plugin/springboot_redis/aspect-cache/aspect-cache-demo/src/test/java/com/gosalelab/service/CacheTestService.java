package com.gosalelab.service;

import com.gosalelab.annotation.CacheEvict;
import com.gosalelab.annotation.CacheInject;
import com.gosalelab.constants.CacheOpType;
import com.gosalelab.pojo.CacheSampleEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CacheTestService {

    @CacheInject(key = "'TestEntity:' + #args[0]", pre = "test_", opType = CacheOpType.WRITE, desc = "test cache write operation")
    public CacheSampleEntity createNewEntity(String name) {
        return new CacheSampleEntity(name, LocalDateTime.now());
    }

    @CacheInject(key = "'TestEntity:' + #args[0]", pre = "test_", opType = CacheOpType.READ_ONLY, desc = "test write sample entity with arguments")
    public  CacheSampleEntity getTestEntity(String name) {
        return null;
    }

    @CacheEvict(key = "'TestEntity:' + #args[0]", pre = "test_", desc = "test evict sample entity with arguments")
    public void delTestEntity(String name) {
    }


    @CacheInject(key = "'TestListEntities:' + #retVal.get(0).getName()", opType = CacheOpType.WRITE)
    public List<CacheSampleEntity> createEntityList(List<CacheSampleEntity> sampleEntities) {
        return sampleEntities;
    }

    @CacheInject(key = "'TestListEntities:' + #args[0]", opType = CacheOpType.READ_ONLY, desc = "test for entity list")
    public List<CacheSampleEntity> getEntityList(String name) {
        return null;
    }

    @CacheEvict(key = "'TestListEntities:' + #args[0]")
    public void delEntityList(String name) {
    }

    @CacheInject(key = "'TestMapEntities:' + #retVal.get(1).getName()", opType = CacheOpType.WRITE)
    public Map<Integer, CacheSampleEntity> createEntityMap(Map<Integer, CacheSampleEntity> sampleEntities) {
        return sampleEntities;
    }

    @CacheInject(key = "'TestMapEntities:' + #args[0]", opType = CacheOpType.READ_ONLY, desc = "get map cache")
    public Map<Integer, CacheSampleEntity> getEntityMap(String name) {
        return null;
    }

    @CacheEvict(key = "'TestMapEntities:' + #args[0]", desc = "delete the map cache")
    public void delEntityMap(String name) {
    }


}
