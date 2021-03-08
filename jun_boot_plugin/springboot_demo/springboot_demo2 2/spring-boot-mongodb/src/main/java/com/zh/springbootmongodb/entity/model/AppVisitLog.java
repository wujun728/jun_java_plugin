package com.zh.springbootmongodb.entity.model;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/14
 */
@Data
@ToString
public class AppVisitLog {

    @Id
    private ObjectId id;

    private String uuid;

    private Integer userId;

    private String ipAddress;

    private String userAgent;

    private String requestUrl;

    private String requestClazz;

    private String requestMethod;

    private String requestParam;

    private Date requestTime;

    private Date responseTime;

    private Long costTime;

    private Integer status;

    private String responseContent;
}
