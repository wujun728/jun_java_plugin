package org.typroject.tyboot.component.cache.pipeline;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.core.foundation.enumeration.StorageMode;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by yaohelang on 2018/7/3.
 */
public class RedisPipeline {


    /**
     * TODO 测试此方法
     * 使用管道批量获取缓存，目前仅支持HASH和VALUE两种类型缓存
     * @param pipelineOperations
     * @return
     */
    public static List<Object> pipelineForGet(List<? extends PipelineOperation> pipelineOperations)
    {
        List<Object> results = Redis.getRedisTemplate().executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (PipelineOperation pipOperation : pipelineOperations) {
                    String key = "";
                    StorageMode storageMode = pipOperation.getStorageMode();
                    switch (storageMode)
                    {
                        case VALUE:
                            key = Redis.genKey(pipOperation.getKey(),pipOperation.getHkey());

                            connection.get(Redis.getRedisTemplate().getKeySerializer().serialize(key));
                            break;
                        case HASH:
                            connection.hGet(Redis.getRedisTemplate().getKeySerializer().serialize(pipOperation.getKey()),
                                    Redis.getRedisTemplate().getHashKeySerializer().serialize(pipOperation.getHkey()));
                            break;
                    }
                }
                return null;
            }
        });

        if(results.size() == pipelineOperations.size())
        {
            for(int i=0;i<results.size();i++)
            {
                PipelineOperation pipelineOperation = pipelineOperations.get(i);
                Object 			result = results.get(i);
                if(!ValidationUtil.isEmpty((pipelineOperation.getResultCallBack())))
                {
                    pipelineOperation.setResult(pipelineOperation.getResultCallBack().processResult(result));
                }else{
                    pipelineOperation.setResult(result);
                }
            }
        }


        return results;
    }


    /**
     * TODO 测试此方法
     * 批量获取缓存，并将结果填入指定列表的对象中，列表中的每个对象都有一组相同的操作，全部保存于 operationForModelsDetails
     * operationForModelsDetails中的元素个数=model列表长度*每个model中的操作个数
     * @param models
     * @param operationForModelsDetails
     * @param <M>
     * @return
     * @throws Exception
     */
    public static  <M> List<M> pipelineGetForModelsDetail(List<M> models,List<OperationForModelsDetail> operationForModelsDetails) throws Exception
    {
        pipelineForGet(operationForModelsDetails);

        int offset = 0;
        for(M m:models)
        {
            for(int i=offset;i<offset+models.size();i++)
            {
                OperationForModelsDetail operation = operationForModelsDetails.get(i);
                String propertyName = operation.getPropertyName();
                Object propertyValue = operation.getResult();
                Bean.setPropertyValue(propertyName,propertyValue,m);
            }
            offset+=models.size();
        }

        return models;

    }


    public static Map<String,Object> pipelineGetFromValue(String... key)
    {
        return null;
    }

}
