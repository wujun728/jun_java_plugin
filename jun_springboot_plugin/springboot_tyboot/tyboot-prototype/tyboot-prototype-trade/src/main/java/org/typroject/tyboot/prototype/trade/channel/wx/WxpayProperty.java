package org.typroject.tyboot.prototype.trade.channel.wx;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wxpay")
public class WxpayProperty {

    private String paymentUrl;
    private String paymentKey;
    private String refundUrl;
    private String appid;
    private String mchid;
    private String cert;
    private String notifyUrl;
}
