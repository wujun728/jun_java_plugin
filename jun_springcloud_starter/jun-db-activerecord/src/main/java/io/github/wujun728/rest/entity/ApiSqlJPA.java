package io.github.wujun728.rest.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name="api_sql")
public class ApiSqlJPA extends Api {
    @Id
    Integer id;
    String group;
    String sqlId;
    @Column(name = "sql_text")
    String sqlText1;
    String datasourceId;
    String before;
    String after;
    Date createTime;
    Date updateTime;

    @Transient
    String method;
    String path;
//    String name;
//    String sql;
//    String params;
//    String status;
//    String datasourceId;
//    String is_select;
//    String previlege;
//    String groupId;
//    String remark;
//    Date createdTime;
//    Date updateTime;
//    String creator;
//    String lastUpdate;
    String transformPlugin;
    String transformPluginParams;
//    String before;
//    String after;

}
