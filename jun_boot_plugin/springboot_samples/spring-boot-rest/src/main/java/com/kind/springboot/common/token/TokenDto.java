package com.kind.springboot.common.token;

/**
 * Function:TokenDto，可以增加字段提高安全性，例如时间戳、URL签名. <br/>
 * Date:     2016年8月11日 下午1:10:19 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
public class TokenDto {

    /**
     * 用户id
     */
    private long userId;

    /**
     * 随机生成的UUID
     */
    private String token;

    public TokenDto(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
