package io.github.wujun728.encryptbody.bean;

import io.github.wujun728.encryptbody.enums.DecryptBodyMethod;
import io.github.wujun728.encryptbody.enums.RSAKeyType;
import lombok.*;

/**
 * <p>解密注解信息</p>
 * @author licoy.cn
 * @version 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptAnnotationInfoBean implements ISecurityInfo {

    private DecryptBodyMethod decryptBodyMethod;

    private String key;

    private RSAKeyType rsaKeyType;

}
