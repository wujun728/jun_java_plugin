package io.github.wujun728.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ApiSql {

    String id;
    String text;
    String type;
    String group;
    @JSONField(name = "datasourceId")
    String datasourceId;
    String before;
    String after;

}
