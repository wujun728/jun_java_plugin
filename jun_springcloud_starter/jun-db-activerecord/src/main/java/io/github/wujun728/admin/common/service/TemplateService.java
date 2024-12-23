package io.github.wujun728.admin.common.service;

import java.util.List;

public interface TemplateService {
    String findAllParent(String childSql,String tableName);
    String permission(String permissionCode,String field);
    String permissionTree(String permissionCode,String field,String tableName);
    String serial(String code);
    String treeSql(String template,String tableName,String ids);
    String addParam(String sql,Object param, List<Object> values);
    String addParamLike(String sql,Object param, List<Object> values);
}
