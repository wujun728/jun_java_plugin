package io.github.wujun728.db.orm.mapping;

import java.util.List;

/**
 * Model映射
 */
public class RedisModelMap {

    //对应表名
    private String key;

    //序列化方式
    private int serialType;

    //属性与数据库字段
    private List<ColumnMap> columnMaps;

    //关联Models
    private List<JoinMap> joinMaps;


}
