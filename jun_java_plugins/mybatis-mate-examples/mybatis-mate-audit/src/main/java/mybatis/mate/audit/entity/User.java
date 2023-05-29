package mybatis.mate.audit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javers.core.metamodel.annotation.PropertyName;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;
    // 该注解可以指定数据审计输出内容
    @PropertyName("手机号码")
    private String mobile;
    private String email;
    private BigDecimal wallet;
    private BigDecimal amount;

}
