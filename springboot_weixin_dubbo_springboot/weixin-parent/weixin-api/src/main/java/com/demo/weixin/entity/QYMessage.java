package com.demo.weixin.entity;

/*
 * @Description: 发送消息结果
 * @version V1.0
 */
public class QYMessage extends BaseWeixinResponse {
    private String invaliduser; //无效的用户ID 会已“|”分割
    private String invalidparty;
    private String invalidtag;

    public String getInvaliduser() {
        return invaliduser;
    }

    public void setInvaliduser(String invaliduser) {
        this.invaliduser = invaliduser;
    }

    public String getInvalidparty() {
        return invalidparty;
    }

    public void setInvalidparty(String invalidparty) {
        this.invalidparty = invalidparty;
    }

    public String getInvalidtag() {
        return invalidtag;
    }

    public void setInvalidtag(String invalidtag) {
        this.invalidtag = invalidtag;
    }

    @Override
    public String toString() {
        return "QYMessage{" +
                "invaliduser='" + invaliduser + '\'' +
                ", invalidparty='" + invalidparty + '\'' +
                ", invalidtag='" + invalidtag + '\'' +
                '}';
    }
}
