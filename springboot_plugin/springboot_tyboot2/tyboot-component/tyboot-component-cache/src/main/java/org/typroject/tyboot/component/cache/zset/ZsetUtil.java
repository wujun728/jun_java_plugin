package org.typroject.tyboot.component.cache.zset;

import org.springframework.data.redis.core.ZSetOperations;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.*;

/**
 * Created by yaohelang on 2018/7/10.
 */
public class ZsetUtil {


    /**
     * zset分页操作，按分数从大到小
     * @param cacheKey  缓存key
     * @param start   开始位置
     * @param length  取得长度
     * @param size    原始大小
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> ZsetPageModel<T> getPageFromZset(String cacheKey , Long start, Long length, Long size) throws Exception
    {

        ZsetPageModel<T>   pageModel = new ZsetPageModel<>();
        Collection<T>  returnList  = new ArrayList<>();
        Long newSize         = Redis.getRedisTemplate().opsForZSet().size(cacheKey); //新的集合大小
        Long offset          = 0L;//偏移量
        if(ValidationUtil.isEmpty(size))
        {
            size = Redis.getRedisTemplate().opsForZSet().size(cacheKey);
        }else{
            offset = newSize - size; //相对于集合新大小的偏移量
        }
        Set<ZSetOperations.TypedTuple<T>> typedTupleSet  = Redis.getRedisTemplate().opsForZSet().reverseRangeWithScores(cacheKey,start+offset,start+offset+length-1);

        if(!ValidationUtil.isEmpty(typedTupleSet))
        {
            SortedSet<ZSetOperations.TypedTuple<T>> sortedSet  = new TreeSet(typedTupleSet);
            for(ZSetOperations.TypedTuple<T> typedtuple:sortedSet)
            {
                T member = typedtuple.getValue();
                returnList.add(member);
            }
        }

        pageModel.setMembers(returnList);
        pageModel.setSize(size);
        return pageModel;
    }

}


