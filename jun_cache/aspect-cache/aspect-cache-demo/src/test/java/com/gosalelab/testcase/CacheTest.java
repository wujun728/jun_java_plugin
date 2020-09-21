package com.gosalelab.testcase;

import com.gosalelab.AspectCacheTest;
import com.gosalelab.pojo.CacheSampleEntity;
import com.gosalelab.service.CacheTestService;
import com.gosalelab.aspect.CacheAspect;
import com.gosalelab.generator.KeyGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AspectCacheTest.class)
public class CacheTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CacheTestService cacheTestService;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    public CacheAspect cacheAspect;

    private CacheSampleEntity entity = new CacheSampleEntity("test", LocalDateTime.now());

    private Object[] arguments = new Object[]{"1111", "2222", entity};

    @Test
    public void testKeyParserWithSimpleName() {
        String simpleKeySpEL = "'test:'+#args[0]";
        String key = keyGenerator.getKey(simpleKeySpEL, arguments, null);
        logger.info(key);
        Assert.assertEquals("test:1111", key);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testKeyParserWithMap() {
        Map mapReturnObj = new HashMap(1);
        mapReturnObj.put("name", "TestMapName");

        String mapKeySpEL = "'test:'+#args[0]+':'+#args[1]+ ':' +#retVal.get('name')";
        String key = keyGenerator.getKey(mapKeySpEL, arguments, mapReturnObj);
        logger.info(key);
        Assert.assertEquals("test:1111:2222:TestMapName", key);
    }

    @Test
    public void testKeyParserWithList() {
        List<String> listReturnObj = new ArrayList<>();
        listReturnObj.add("ListName");

        String listKeySpEL = "'test:'+#args[0]+':'+#args[1]+ ':' +#retVal.get(0) + ':' + #args[2].getName()";
        String key = keyGenerator.getKey(listKeySpEL, arguments, listReturnObj);
        logger.info(key);

        Assert.assertEquals("test:1111:2222:ListName:test", key);
    }

    @Test
    public void testPutGetCache(){
        logger.info(cacheAspect.toString());
        cacheTestService.createNewEntity("test name");
        CacheSampleEntity sample = cacheTestService.getTestEntity("test name");
        Assert.assertEquals("test name", sample.getName());
    }

    @Test
    public void testDelCache(){
        String delName = "test name";
        cacheTestService.createNewEntity(delName);
        cacheTestService.delTestEntity(delName);
    }

    @Test
    public void testListValue() {
        List<CacheSampleEntity> sampleEntities = new ArrayList<>();
        CacheSampleEntity entity1 = new CacheSampleEntity("TestName1", LocalDateTime.now());
        CacheSampleEntity entity2 = new CacheSampleEntity("TestName2", LocalDateTime.now());
        sampleEntities.add(entity1);
        sampleEntities.add(entity2);

        cacheTestService.createEntityList(sampleEntities);
        List<CacheSampleEntity> entities = cacheTestService.getEntityList("TestName1");
        Assert.assertEquals("TestName1", entities.get(0).getName());
        Assert.assertEquals("TestName2", entities.get(1).getName());
    }

    @Test
    public void testMapValue() {
        Map<Integer, CacheSampleEntity> mapValues = new HashMap<>();
        CacheSampleEntity entity1 = new CacheSampleEntity("TestName1", LocalDateTime.now());
        CacheSampleEntity entity2 = new CacheSampleEntity("TestName2", LocalDateTime.now());
        mapValues.put(1, entity1);
        mapValues.put(2, entity2);

        logger.info(mapValues.get(1).getName());
        logger.info(mapValues.get(2).getName());

        cacheTestService.createEntityMap(mapValues);
        Map<Integer, CacheSampleEntity> returnEntities = cacheTestService.getEntityMap("TestName1");

        logger.info(returnEntities.toString());

        Assert.assertEquals("TestName1", returnEntities.get(1).getName());
        Assert.assertEquals("TestName2", returnEntities.get(2).getName());
    }

}
