package io.github.wujun728.groovy.common.model;

import io.github.wujun728.rest.entity.ApiSql;
import lombok.Data;

import java.util.List;

@Data
public class ApiConfig {
	
    private Integer id;
    private Integer pid;
    private String path;
    private String name;
    private String method;
    private String params;
    private String beanName;
//    private String className;
    private String datasourceId;
    private String scriptType;
//    private String BeanType;
    private String scriptContent;
//    private String groovyContent;
    private String status;
    private Integer sort;
    private String extendInfo;
    private Integer openTrans;
    private String resutltParams;
    private String creator;
    private String createdTime;
    private String updateTime;
    private String lastUpdate;

    List<ApiSql> sqlList;
    String cachePlugin;
    String contentType;

}
