package org.typroject.tyboot.prototype.trade.channel.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperty {

    private String publicKey;//公钥
    private String privateKey;//私钥
    private String appid;//应用id
    private String serverUrl;//交易地址
    private String notifyUrl;//回调地址

}
