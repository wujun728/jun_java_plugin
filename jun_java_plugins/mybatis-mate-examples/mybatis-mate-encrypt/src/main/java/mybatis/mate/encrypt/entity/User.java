package mybatis.mate.encrypt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;
    // 指定加密算法
    @FieldEncrypt(algorithm = Algorithm.PBEWithMD5AndDES)
    private String password;
    @FieldEncrypt
    private String email;
    @FieldEncrypt(algorithm = Algorithm.MD5_32)
    private String md5;
    @FieldEncrypt(algorithm = Algorithm.RSA)
    private String rsa;

}
