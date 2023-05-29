package mybatis.mate.dict.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mybatis.mate.annotation.JsonBind;
import mybatis.mate.dict.config.JsonBindStrategy;

@Getter
@Setter
@ToString
@JsonBind(JsonBindStrategy.Type.departmentRole)
public class User {
    private Long id;
    private String username;
    private Integer sex;
    private Integer status;

}
