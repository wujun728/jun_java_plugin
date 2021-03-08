package com.zh.springbootmongodb.service;

import com.zh.springbootmongodb.entity.model.AppVisitLog;

import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/14
 */
public interface AppVisitLogService {

    void save(String uuid, String ipAddress, String userAgent, String requestUrl, String requestClazz, String requestMethod, String requestParam, Date requestTime);

    void save(String uuid, Integer userId, String ipAddress, String userAgent, String requestUrl, String requestClazz, String requestMethod, String requestParam, Date requestTime,Integer status);

    void save(String uuid, Date responseTime, Long costTime, String responseContent,Integer status);

    AppVisitLog findByUuidAndCreateTime(String uuid,Date createTime);
}
