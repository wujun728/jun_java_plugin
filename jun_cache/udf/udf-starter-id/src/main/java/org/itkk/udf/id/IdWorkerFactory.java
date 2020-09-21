package org.itkk.udf.id;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.itkk.udf.cache.redis.CacheRedisProperties;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.constant.IdWorkerConstant;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.itkk.udf.id.domain.CacheValue;
import org.itkk.udf.id.domain.IdWorkerWrapper;
import org.itkk.udf.rms.Rms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * IdWorkerFactory
 */
@Component
@Slf4j
public class IdWorkerFactory {

    /**
     * MAX_SEQUENCE
     */
    public static final int MAX_SEQUENCE = 4096;

    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "id";

    /**
     * 分隔符
     */
    private static final String SPLIT = ":";

    /**
     * key的过期时间
     */
    private static final long EXPIRATION = 60;

    /**
     * job执行时间
     */
    private static final long JOB_RUN_TIME = 50 * 1000l;

    /**
     * 本地缓存 ( 线程安全 )
     */
    private Map<String, IdWorker> localCache = new ConcurrentHashMap<>();

    /**
     * ip
     */
    private String host;

    /**
     * 端口号
     */
    @Value("${server.port}")
    private int port;

    /**
     * idWorkerProperties
     */
    @Autowired
    private IdWorkerProperties idWorkerProperties;

    /**
     * rms
     */
    @Autowired
    private Rms rms;

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * cacheRedisProperties
     */
    @Autowired
    private CacheRedisProperties cacheRedisProperties;

    /**
     * 构造函数
     *
     * @throws UnknownHostException UnknownHostException
     */
    public IdWorkerFactory() throws UnknownHostException {
        this.host = InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获得分布式ID
     *
     * @return 分布式ID
     */
    public String nextId() {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get();
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return String.valueOf(idWorker.nextId());
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            // 请求 & 返回
            return this.rmsNexId(idWorkerWrapper, "ID_1", uriVariables);
        }
    }

    /**
     * 获得分布式ID
     *
     * @param dwId 数据中心ID | 机器ID ( 0 - 1023 )
     * @return 分布式ID
     */
    public String nextId(long dwId) {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get(dwId);
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return String.valueOf(idWorker.nextId());
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("dwId", dwId);
            // 请求 & 返回
            return this.rmsNexId(idWorkerWrapper, "ID_2", uriVariables);
        }
    }

    /**
     * 获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @return 分布式ID
     */
    public String nextId(long datacenterId, long workerId) {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get(datacenterId, workerId);
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return String.valueOf(idWorker.nextId());
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("datacenterId", datacenterId);
            uriVariables.put("workerId", workerId);
            // 请求 & 返回
            return this.rmsNexId(idWorkerWrapper, "ID_3", uriVariables);
        }
    }

    /**
     * 批量获得分布式ID
     *
     * @param count count
     * @return 分布式ID
     */
    public List<String> batchNextId(int count) {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get();
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return this.localBatchNexId(idWorker, count);
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("count", count); // NOSONAR
            // 请求 & 返回
            return this.rmsBatchNexId(idWorkerWrapper, count, "ID_4", uriVariables);
        }
    }

    /**
     * 批量获得分布式ID
     *
     * @param dwId  数据中心ID | 机器ID ( 0 - 1023 )
     * @param count count
     * @return 分布式ID
     */
    public List<String> batchNextId(long dwId, int count) {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get(dwId);
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return this.localBatchNexId(idWorker, count);
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("dwId", dwId);
            uriVariables.put("count", count); // NOSONAR
            // 请求 & 返回
            return this.rmsBatchNexId(idWorkerWrapper, count, "ID_5", uriVariables);
        }
    }

    /**
     * 批量获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @param count        count
     * @return 分布式ID
     */
    public List<String> batchNextId(long datacenterId, long workerId, int count) {
        //获得ID生成器
        IdWorkerWrapper idWorkerWrapper = this.get(datacenterId, workerId);
        IdWorker idWorker = idWorkerWrapper.getIdWorker();
        //判断生成方式
        if (idWorker != null) { //本地生成
            return this.localBatchNexId(idWorker, count);
        } else { //远程生成
            //构建uriVariables
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("datacenterId", datacenterId);
            uriVariables.put("workerId", workerId);
            uriVariables.put("count", count); // NOSONAR
            // 请求 & 返回
            return this.rmsBatchNexId(idWorkerWrapper, count, "ID_6", uriVariables);
        }
    }

    /**
     * 本地批量获取ID
     *
     * @param idWorker idWorker
     * @param count    count
     * @return ID
     */
    private List<String> localBatchNexId(IdWorker idWorker, Integer count) {
        //判断最大数量
        if (count > idWorkerProperties.getMaxCount()) {
            throw new SystemRuntimeException("The number is too large and the maximum setting is " + idWorkerProperties.getMaxCount());
        }
        //批量生成
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ids.add(Long.toString(idWorker.nextId()));
        }
        return ids;
    }

    /**
     * 远程获取ID
     *
     * @param idWorkerWrapper ID生成器包装类
     * @param serviceCode     服务代码
     * @param uriVariables    参数
     * @return ID
     */
    private String rmsNexId(IdWorkerWrapper idWorkerWrapper, String serviceCode, Map<String, Object> uriVariables) {
        try {
            //远程请求
            ResponseEntity<RestResponse<String>> result = rms.call(idWorkerWrapper.getCacheValue().getHost(), idWorkerWrapper.getCacheValue().getPort(), serviceCode, null, null, new ParameterizedTypeReference<RestResponse<String>>() {
            }, uriVariables);
            //返回
            return result.getBody().getResult();
        } catch (Exception e) {
            //远程请求失败,快速剔除远程节点,本地重试
            log.warn("rmsNexId failure , start fast switching ,  error ---------> {}", e.getMessage());
            redisTemplate.delete(idWorkerWrapper.getCacheKey());
            this.localCache.remove(idWorkerWrapper.getCacheKey());
            return this.nextId(idWorkerWrapper.getDatacenterId(), idWorkerWrapper.getWorkerId());
        }
    }

    /**
     * 远程批量获取ID
     *
     * @param idWorkerWrapper ID生成器包装类
     * @param count           数量
     * @param serviceCode     服务代码
     * @param uriVariables    参数
     * @return ID
     */
    private List<String> rmsBatchNexId(IdWorkerWrapper idWorkerWrapper, int count, String serviceCode, Map<String, Object> uriVariables) {
        try {
            //添加参数
            uriVariables.put("count", count);
            //远程请求
            ResponseEntity<RestResponse<List<String>>> result = rms.call(idWorkerWrapper.getCacheValue().getHost(), idWorkerWrapper.getCacheValue().getPort(), serviceCode, null, null, new ParameterizedTypeReference<RestResponse<List<String>>>() {
            }, uriVariables);
            //返回
            return result.getBody().getResult();
        } catch (Exception e) {
            //远程请求失败,快速剔除远程节点,本地重试
            log.warn("rmsBatchNexId failure , start fast switching , error ---------> {}", e.getMessage());
            redisTemplate.delete(idWorkerWrapper.getCacheKey());
            this.localCache.remove(idWorkerWrapper.getCacheKey());
            return this.batchNextId(idWorkerWrapper.getDatacenterId(), idWorkerWrapper.getWorkerId(), count);
        }
    }

    /**
     * 返回ID ( 按时间戳 )
     *
     * @return IdWorkerWrapper
     */
    private synchronized IdWorkerWrapper get() {
        long dwId = System.currentTimeMillis() & ~(-1L << (IdWorkerConstant.WORKER_ID_BITS + IdWorkerConstant.DATACENTER_ID_BITS));
        return this.get(dwId);
    }

    /**
     * 获得ID生成器
     *
     * @param dwId 数据中心ID | 机器ID ( 0 - 1023 )
     * @return IdWorkerWrapper
     */
    private IdWorkerWrapper get(long dwId) {
        long workerId = dwId & ~(-1L << IdWorkerConstant.WORKER_ID_BITS);
        long datacenterId = (dwId >> IdWorkerConstant.WORKER_ID_BITS) & ~(-1L << IdWorkerConstant.DATACENTER_ID_BITS);
        return this.get(datacenterId, workerId);
    }

    /**
     * 获得ID生成器
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @return IdWorkerWrapper
     */
    private IdWorkerWrapper get(long datacenterId, long workerId) { // NOSONAR
        //值判断
        if (workerId > IdWorkerConstant.MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", IdWorkerConstant.MAX_WORKER_ID));
        }
        if (datacenterId > IdWorkerConstant.MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", IdWorkerConstant.MAX_DATACENTER_ID));
        }
        //生成key
        String key = cacheRedisProperties.getPrefix().concat(SPLIT).concat(CACHE_NAME).concat(SPLIT).concat(Long.toString(datacenterId)).concat(SPLIT).concat(Long.toString(workerId));
        //定义返回值
        IdWorkerWrapper idWorkerWrapper = new IdWorkerWrapper();
        //设置缓存中心key
        idWorkerWrapper.setCacheKey(key);
        //设置数据中心ID
        idWorkerWrapper.setDatacenterId(datacenterId);
        //设置机器ID
        idWorkerWrapper.setWorkerId(workerId);
        //查询本地
        IdWorker idWorker = this.localCache.get(key);
        //判空
        if (idWorker == null) { //不存在本地缓存
            //判断缓存是否存在
            if (redisTemplate.hasKey(key)) { //缓存存在
                //获得缓存数据
                CacheValue cacheValue = (CacheValue) redisTemplate.opsForValue().get(key);
                //比较本地的IP和端口号
                if (cacheValue.getHost().equals(this.host) && cacheValue.getPort() == this.port) { //一致,同步本地缓存
                    //获得idWorker , 同步本地缓存
                    idWorker = this.putNxLocalCache(key, datacenterId, workerId);
                } else { //不一致 , 走远程调用
                    //设置
                    idWorkerWrapper.setCacheValue(cacheValue);
                    //返回
                    return idWorkerWrapper;
                }
            } else { //缓存不存在
                //尝试锁
                if (this.lock(key)) { //锁成功
                    //获得idWorker
                    idWorker = this.putNxLocalCache(key, datacenterId, workerId);
                } else { //锁失败
                    //递归重试
                    return this.get(datacenterId, workerId);
                }
            }
        }
        //设置
        idWorkerWrapper.setIdWorker(idWorker);
        //返回
        return idWorkerWrapper;
    }

    /**
     * 定时刷新过期时间
     */
    @Scheduled(fixedRate = JOB_RUN_TIME)
    public void refresh() {
        //开始时间
        Date start = new Date();
        //判断本地缓存
        if (MapUtils.isNotEmpty(this.localCache)) {
            //获得迭代器
            Iterator<String> keys = this.localCache.keySet().iterator();
            //循环迭代
            while (keys.hasNext()) {
                //刷新
                this.refresh(keys.next());
            }
        }
        //结束时间
        Date end = new Date();
        //日志打印
        log.info("refresh end ,local size {} time = {} ms", this.localCache.size(), (end.getTime() - start.getTime()));
    }

    /**
     * 刷新缓存过期时间
     *
     * @param key 缓存key
     */
    private void refresh(String key) { // NOSONAR
        //常量
        final int num2 = 2;
        //得到 datacenterId &  workerId
        String[] keyArr = key.split(SPLIT);
        long datacenterId = Long.parseLong(keyArr[keyArr.length - num2]);
        long workerId = Long.parseLong(keyArr[keyArr.length - 1]);
        //结果
        boolean execute = false;
        //判空
        if (this.localCache.containsKey(key)) {
            //判断缓存是否存在
            if (redisTemplate.hasKey(key)) { //存在
                //获得值(用于确定锁是否是自己创建的)
                CacheValue cacheValue = (CacheValue) redisTemplate.opsForValue().get(key);
                //比较(如果值是一样,则代表自己拥有锁,如果值不一样,则代表锁已经被其他进程获取)
                if (cacheValue.getHost().equals(this.host) && cacheValue.getPort() == this.port) {
                    //设置超时时间
                    redisTemplate.expire(key, EXPIRATION, TimeUnit.SECONDS);
                    //设置返回值
                    execute = true;
                } else {
                    //排除本地缓存
                    this.localCache.remove(key);
                }
            } else { //不存在
                //创建锁
                if (this.lock(key)) { //锁成功
                    //创建本地缓存
                    this.putNxLocalCache(key, datacenterId, workerId);
                    //设置返回值
                    execute = true;
                } else { //锁失败
                    //排除本地缓存
                    this.localCache.remove(key);
                }
            }
            //日志(刷新失败打印日志)
            if (!execute) {
                log.info("refresh {} , {}", execute, key);
            }
        }
    }

    /**
     * 原子锁 (悲观锁)
     *
     * @param key 键
     * @return 结果
     */
    private boolean lock(String key) {
        //构造cacheValue
        CacheValue localCacheValue = new CacheValue();
        localCacheValue.setPort(this.port);
        localCacheValue.setHost(this.host);
        //检查是否是死锁
        if (redisTemplate.hasKey(key)) {
            //获得缓存过期时间
            long expiration = redisTemplate.getExpire(key);
            //如果等于-1,则代表死锁
            if (expiration == -1) {
                //删除缓存
                redisTemplate.delete(key);
            }
        }
        //创建锁
        boolean lock = redisTemplate.opsForValue().setIfAbsent(key, localCacheValue);
        //锁失败
        if (!lock) {
            //获得数据
            CacheValue cacheValue = (CacheValue) redisTemplate.opsForValue().get(key);
            //比对host和port,是否跟当前server一致,如果一致的话,证明是可以获取锁的
            if (cacheValue.getHost().equals(this.host) && cacheValue.getPort() == this.port) {
                //更改锁定状态
                lock = true;
            }
        }
        //锁成功
        if (lock) {
            //设置超时时间
            redisTemplate.expire(key, EXPIRATION, TimeUnit.SECONDS);
        }
        //返回
        return lock;
    }

    /**
     * 设置本地缓存 ( 防止重复实例化 )
     *
     * @param key          key
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @return IdWorker
     */
    private IdWorker putNxLocalCache(String key, long datacenterId, long workerId) {
        //判断是否存在 , 防止重复实例化
        if (!this.localCache.containsKey(key)) {
            this.localCache.put(key, new IdWorker(workerId, datacenterId));
        }
        //返回
        return this.localCache.get(key);
    }
}
