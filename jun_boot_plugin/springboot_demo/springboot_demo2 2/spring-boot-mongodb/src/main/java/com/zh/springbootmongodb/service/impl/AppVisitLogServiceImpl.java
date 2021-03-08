package com.zh.springbootmongodb.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zh.springbootmongodb.base.constance.DateConstance;
import com.zh.springbootmongodb.base.constance.MongoDBConstance;
import com.zh.springbootmongodb.entity.model.AppVisitLog;
import com.zh.springbootmongodb.service.AppVisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/14
 */
@Slf4j
@Service
public class AppVisitLogServiceImpl implements AppVisitLogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取当天的集合名
     * 按天分表
     * @return
     */
    private String getCollectionName() {
        String collectionName = MongoDBConstance.COLLECTION_NAME_PRE_APP_VISIT_LOG + DateTimeFormatter.ofPattern(DateConstance.FORMATTER_YYYY_MM_DD).format(LocalDate.now());
        if (!this.mongoTemplate.collectionExists(collectionName)){
            this.mongoTemplate.createCollection(collectionName);
            this.mongoTemplate.indexOps(collectionName).ensureIndex(new Index().on("uuid", Sort.Direction.ASC).unique());
        }
        return collectionName;
    }

    /**
     * 根据日志创建日期获取集合名
     * @param createTime
     * @return
     */
    private String getCollectionName(Date createTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault());
        return MongoDBConstance.COLLECTION_NAME_PRE_APP_VISIT_LOG + DateTimeFormatter.ofPattern(DateConstance.FORMATTER_YYYY_MM_DD).format(localDateTime.toLocalDate());
    }

    @Override
    public void save(String uuid, String ipAddress, String userAgent, String requestUrl, String requestClazz, String requestMethod, String requestParam, Date requestTime) {
        this.save(uuid, null, ipAddress, userAgent, requestUrl, requestClazz, requestMethod, requestParam, requestTime,null);
    }

    @Override
    public void save(String uuid, Integer userId, String ipAddress, String userAgent, String requestUrl, String requestClazz, String requestMethod, String requestParam, Date requestTime,Integer status) {
        AppVisitLog appVisitLog = new AppVisitLog();
        appVisitLog.setUuid(uuid);
        appVisitLog.setUserId(userId);
        appVisitLog.setIpAddress(ipAddress);
        appVisitLog.setUserAgent(userAgent);
        appVisitLog.setRequestUrl(requestUrl);
        appVisitLog.setRequestClazz(requestClazz);
        appVisitLog.setRequestMethod(requestMethod);
        appVisitLog.setRequestParam(requestParam);
        appVisitLog.setRequestTime(requestTime);
        appVisitLog.setStatus(status);
        this.mongoTemplate.save(appVisitLog,this.getCollectionName());
    }

    @Override
    public void save(String uuid, Date responseTime, Long costTime, String responseContent,Integer status) {
        Query query = Query.query(Criteria.where("uuid").is(uuid));
        Update update = Update.update("responseTime", responseTime).set("costTime", costTime).set("responseContent",responseContent).set("status",status);
        this.mongoTemplate.updateFirst(query,update,this.getCollectionName());
    }

    @Override
    public AppVisitLog findByUuidAndCreateTime(String uuid,Date createTime) {
        Query query = Query.query(Criteria.where("uuid").is(uuid));
        return this.mongoTemplate.findOne(query,AppVisitLog.class,this.getCollectionName(createTime));
    }
}
