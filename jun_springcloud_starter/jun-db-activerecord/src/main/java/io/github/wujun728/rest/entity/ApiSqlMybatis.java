package io.github.wujun728.rest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

@TableName("api_sql")
@Data
public class ApiSqlMybatis extends Api {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String group;
    String sqlId;
    @TableField("sql_text")
    String sqlTextq;
    @TableField(value = "datasource_id")
    String datasourceId;
    String before;
    String after;
    Date createTime;
    Date updateTime;

    @TableField(value = "datasource_id",exist = false)
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
