package io.github.wujun728.encryptbody.bean;

import io.github.wujun728.encryptbody.enums.EncryptBodyMethod;
import io.github.wujun728.encryptbody.enums.RSAKeyType;
import io.github.wujun728.encryptbody.enums.SHAEncryptType;
import lombok.*;

/**
 * <p>加密注解信息</p>
 *
 * @author licoy.cn
 * @version 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptAnnotationInfoBean implements ISecurityInfo {

    private EncryptBodyMethod encryptBodyMethod;

    private SHAEncryptType shaEncryptType;

    private String key;

    private RSAKeyType rsaKeyType;

}
