package io.github.wujun728.online.common;

import org.hibernate.validator.constraints.Range;
import lombok.Data;

/**
 * 查询公共参数
 *
 */
@Data
public class Query {
    String code;
    String tableName;
    String attrType;
    String columnType;
    String connName;
    String dbType;
    String projectName;

    Integer page;

    @Range(min = 1, max = 1000, message = "每页条数，取值范围 1-1000")
    Integer limit;
}
