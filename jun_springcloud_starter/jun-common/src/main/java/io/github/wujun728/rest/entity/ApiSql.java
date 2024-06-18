package io.github.wujun728.rest.entity;

import lombok.Data;

@Data
public class ApiSql extends Api {

    Integer id;

    String sqlId;

    String sqlText;

    String datasourceId;

    String transformPlugin;

    String transformPluginParams;

    String group;

    String before;

    String after;


}
