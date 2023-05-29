package mybatis.mate.sharding.jta.atomikos.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志表
 */
@Getter
@Setter
public class Log {
    // 注解 ID
    private Long id;
    // 日志内容
    private String content;

}
