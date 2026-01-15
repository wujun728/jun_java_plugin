package io.github.wujun728.sql.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class SqlWithParam {

    private String namespace;
    private String sqlId;
    private Map<String, Object> parameters;

}
