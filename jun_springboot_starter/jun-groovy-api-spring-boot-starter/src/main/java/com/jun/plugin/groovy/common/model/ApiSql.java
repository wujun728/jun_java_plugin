package com.jun.plugin.groovy.common.model;

import lombok.Data;

@Data
public class ApiSql {

    Integer id;

    String apiId;

    String sqlText;

    String transformPlugin;

    String transformPluginParams;

}
