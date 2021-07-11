package com.battcn.boot.extend.configuration.commons;

/**
 * 扩展包所有 bean 名字
 *
 * @author Levin
 * @since 2019-07-30
 **/
public interface ExtendBeanTemplate {

    String TRUE = "true";
    String ENABLED = "enabled";


    String REDIS = "extend.redis";
    String REDIS_LOCK = "extend.redis.lock";
    String REDIS_LOCK_INTERCEPTOR = "extend.redis.lock.interceptor";


    String REDIS_LIMIT = "extend.redis.limit";
    String REDIS_LIMIT_INTERCEPTOR = "extend.redis.limit.interceptor";


    String REDIS_LOCK_HELPER = "redisLockHelper";
    String REDIS_LIMIT_TEMPLATE = "redisLimitTemplate";
    String REDIS_KEY_GENERATOR = "redisKeyGenerator";

    String I18N = "extend.i18n";
    String CORS = "extend.cors";
    String CRYPTO = "extend.crypto";
    String MYBATIS_PLUGIN = "extend.mybatis.plugin";
    String SENSITIVE = "extend.sensitive";
    String TIME = "extend.time";
    String WRAPPER_FILTER = "extend.wrapper.filter";
    String XSS_FILTER = "extend.xss.filter";



    String XXL_JOB = "extend.xxl.job";









    /*  OSS 云存储的    */
    /**
     * 配置文件前缀
     */
    String OSS_CONFIG_PREFIX_ALIYUN = "extend.oss.cloud.aliyun";
    String OSS_CONFIG_PREFIX_MINIO = "extend.oss.cloud.minio";
    String OSS_CONFIG_PREFIX_QINIU = "extend.oss.cloud.qiniu";
    String OSS_CONFIG_PREFIX_TENCENT = "extend.oss.cloud.tencent";


    String MINIO_STORAGE_OPERATION = "minioStorageOperation";
    String ALI_YUN_STORAGE_OPERATION = "aliYunStorageOperation";
    String QI_NIU_STORAGE_OPERATION = "qiNiuStorageOperation";
    String TENCENT_STORAGE_OPERATION = "tencentStorageOperation";


}