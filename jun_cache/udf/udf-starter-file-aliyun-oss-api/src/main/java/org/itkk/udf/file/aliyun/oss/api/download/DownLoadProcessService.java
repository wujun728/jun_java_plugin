package org.itkk.udf.file.aliyun.oss.api.download;

import org.itkk.udf.core.exception.ParameterValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * DownLoadProcessService
 */
@Service
public class DownLoadProcessService {

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * generateDownLoadId
     *
     * @param count count
     * @return String
     */
    public String generateDownLoadId(long count) {
        //判断行数
        if (count > DownConstant.MAX_DOWNLOAD_ROW_COUNT) {
            throw new ParameterValidException("导出行数不能超过" + DownConstant.MAX_DOWNLOAD_ROW_COUNT + "行", null);
        }
        //生成ID
        String id = UUID.randomUUID().toString();
        //获得key
        String key = DownLoadProcessAspect.DOWNLOAD_CACHE_PREFIX + id;
        //构造对象
        DownloadInfo info = new DownloadInfo();
        info.setId(id);
        info.setStatus(DownConstant.DOWNLOAD_PROCESS_STATUS.STATUS_1.value());
        //更新缓存
        redisTemplate.opsForValue().set(key, info, DownLoadProcessAspect.DOWNLOAD_WAITING_CACHE_EXPIRATION, TimeUnit.MINUTES);
        //返回ID
        return id;
    }

    /**
     * get
     *
     * @param id id
     * @return DownloadInfo
     */
    public DownloadInfo getDownloadInfo(String id) {
        //获得key
        String key = DownLoadProcessAspect.DOWNLOAD_CACHE_PREFIX + id;
        //获得缓存值
        Object cacheValue = redisTemplate.opsForValue().get(key);
        //返回
        return cacheValue == null ? null : (DownloadInfo) cacheValue;
    }
}
