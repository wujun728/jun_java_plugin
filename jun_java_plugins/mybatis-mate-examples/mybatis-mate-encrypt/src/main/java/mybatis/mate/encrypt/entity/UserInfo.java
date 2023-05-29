package mybatis.mate.encrypt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Getter
@Setter
@ToString
public class UserInfo {
    private Long id;
    private Long userId;
    @FieldEncrypt(algorithm = Algorithm.RSA)
    private String rsa;

}
