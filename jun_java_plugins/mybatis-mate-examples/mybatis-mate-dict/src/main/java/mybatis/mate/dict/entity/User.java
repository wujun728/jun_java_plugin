package mybatis.mate.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mybatis.mate.annotation.FieldBind;
import mybatis.mate.dict.config.BindType;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;

    /**
     * type 绑定类型 ，target 目标显示属性
     */
    @FieldBind(type = BindType.USER_SEX, target = "sexText")
    private Integer sex;

    // 绑定显示属性，非表字典（排除）
    @TableField(exist = false)
    private String sexText;

    @FieldBind(type = BindType.USER_STATUS, target = "statusEnum")
    private Integer status;

    @TableField(exist = false)
    private StatusEnum statusEnum;

}
