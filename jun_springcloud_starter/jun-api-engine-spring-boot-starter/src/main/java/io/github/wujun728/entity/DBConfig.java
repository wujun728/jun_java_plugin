package io.github.wujun728.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("apisql.config")
public class DBConfig {
    String sql;

    String datasource;

    public String getSql() {
        if(sql!=null){
            return sql;
        }else{
            return "classpath:sql/*.xml";
        }
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDatasource() {
        if(datasource!=null){
            return datasource;
        }else{
            return "classpath:ds.xml";
        }
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
