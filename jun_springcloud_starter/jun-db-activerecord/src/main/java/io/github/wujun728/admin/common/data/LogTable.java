package io.github.wujun728.admin.common.data;

import io.github.wujun728.admin.common.BaseData;
import lombok.Data;

/***
 * @date 2023-03-31 13:52:25
 * @remark 记录日志的表
 */
@Data
public class LogTable extends BaseData {
    //表名
    private String tableName;
    //注释
    private String tableComment;
    //开启缓存
    private String openCache;
    //缓存key字段列表
    private String cacheKeyFields;
    //保存日志
    private String saveLog;
}
