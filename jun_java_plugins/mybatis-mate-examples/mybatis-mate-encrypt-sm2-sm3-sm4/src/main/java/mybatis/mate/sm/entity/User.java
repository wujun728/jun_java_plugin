package mybatis.mate.sm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldBind;
import mybatis.mate.annotation.FieldEncrypt;
import mybatis.mate.encrypt.KeyGenerator;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;

    // 指定加密算法
    @FieldEncrypt(algorithm = Algorithm.PBEWithMD5AndDES)
    private String password;

    /**
     * 注意 SM2 SM3 SM4 加密算法必须依赖 bouncycastle 库，
     */
    @FieldEncrypt(algorithm = Algorithm.SM4)
    private String email;

    /**
     * 非对称加密需要全局指定公钥私钥
     * {@link KeyGenerator#generateSM2Keys()} 这个生成密钥对 0 公钥 1 私钥
     */
    @FieldEncrypt(algorithm = Algorithm.SM2)
    private String sm2;

    /**
     * 加密数据绑定混合
     */
    @FieldEncrypt(algorithm = Algorithm.SM3)
    @FieldBind(type = "test_bind", target = "sm3Text")
    private String sm3;

    @TableField(exist = false)
    private String sm3Text;

}
