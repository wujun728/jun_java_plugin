package io.github.wujun728.online.vo.query;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 下拉选择查询组件
 * 用于生成下拉选择类型字段的查询配置
 */
public class SelectQuery extends AbstractQuery {

    /**
     * 构造函数
     * @param column 表字段实体
     */
    public SelectQuery(OnlineTableColumnEntity column) {
        this.column = column;
    }

    /**
     * 获取下拉选择查询组件配置
     * @return 查询组件配置Map
     */
    @Override
    public Map<String, Object> getQuery() {
        // 创建组件属性Map
        Map<String, Object> componentProps = new HashMap<>();
        
        // 设置组件标签（显示名称）
        componentProps.put("label", column.getComments());
        
        // 设置是否允许清空选择
        componentProps.put("allowClear", true);
        
        // 创建字典项数组
        // 实际项目中，这些字典项应该从数据库或配置中获取
        Map<String, String> dictItem1 = new HashMap<>();
        dictItem1.put("label", "启用");
        dictItem1.put("value", "1");
        
        Map<String, String> dictItem2 = new HashMap<>();
        dictItem2.put("label", "禁用");
        dictItem2.put("value", "0");
        
        // 设置字典数据
        componentProps.put("options", Arrays.asList(dictItem1, dictItem2));
        
        // 创建查询配置Map
        Map<String, Object> queryMap = new HashMap<>();
        
        // 设置查询控件类型
        queryMap.put("queryType", "select");
        
        // 设置字段名称
        queryMap.put("name", column.getName());
        
        // 关联组件属性
        queryMap.put("config", componentProps);
        
        return queryMap;
    }
}

