package mybatis.mate.sm.mysql.aes.entity;

import lombok.Getter;
import lombok.Setter;
import mybatis.mate.annotation.FieldEncrypt;

import java.util.Date;

@Getter
@Setter
public class AttrVO extends ComAttr {

    // 指定密钥（子类注解会覆盖父类）
    @FieldEncrypt(password = "123456789")
    private String mobile;

    private Date time;

}
