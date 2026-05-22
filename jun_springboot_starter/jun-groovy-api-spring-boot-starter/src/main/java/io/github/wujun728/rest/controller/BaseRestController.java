package io.github.wujun728.rest.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.rest.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * REST API 控制器基类
 * 提取公共初始化和参数处理逻辑
 */
@Slf4j
public abstract class BaseRestController {

    protected String ds = "main";

    /**
     * 从请求中提取通用参数并初始化，返回的Map包含 entityName, tableName, isUnderLine 等公共参数
     *
     * @param entityName 实体名称
     * @param request HTTP请求对象
     * @return 包含参数的Map对象，其中 tableName 是原始 entityName 转换后的下划线格式
     */
    protected Map<String, Object> initCommonParameters(String entityName, HttpServletRequest request) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        this.ds = MapUtil.getStr(parameters, "ds", "main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);

        parameters.put("entityName", entityName);
        parameters.put("tableName", tableName);
        parameters.put("isUnderLine", isUnderLine);

        return parameters;
    }

    /**
     * 从参数中获取 tableName（已转换为下划线格式）
     * @param parameters 参数Map
     * @return tableName
     */
    protected String getTableName(Map<String, Object> parameters) {
        return MapUtil.getStr(parameters, "tableName", "");
    }

    /**
     * 获取当前数据源标识
     */
    protected String getCurrentDs() {
        return this.ds;
    }
}
