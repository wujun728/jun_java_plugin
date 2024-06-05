package com.sms.starter.dto;

import lombok.Data;

/**
 * SMSDTO参数类
 *
 * @Author Sans
 * @CreateTime 2019/4/20
 * @attention
 */
@Data
public class SendSMSDTO {
    /**
     * 模板ID
     */
    private String templateid;
    /**
     * 参数
     */
    private String param;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户穿透ID，可以为空
     */
    private String uid;
}
