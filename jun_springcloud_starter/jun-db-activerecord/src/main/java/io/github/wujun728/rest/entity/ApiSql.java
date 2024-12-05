package io.github.wujun728.rest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data

public class ApiSql extends Api {

    Integer id;
    String group;
    String sqlId;
    String sqlText;
    String datasourceId;
    String before;
    String after;
    Date createTime;
    Date updateTime;

//    String method;
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
