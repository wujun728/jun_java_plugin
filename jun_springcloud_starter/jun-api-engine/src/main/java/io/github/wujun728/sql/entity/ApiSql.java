package io.github.wujun728.sql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ApiSql {

    String id;
    String group;
    String path;
    String text;
    String type;
    @JSONField(name = "datasourceId")
    String datasourceId;
    String before;
    String after;

}
