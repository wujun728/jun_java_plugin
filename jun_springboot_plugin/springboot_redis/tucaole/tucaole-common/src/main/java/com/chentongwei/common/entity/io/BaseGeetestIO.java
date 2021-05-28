package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 基础的极验验证io，登录注册用的参数
 **/
@Data
public class BaseGeetestIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 极验验证二次验证表单数据 chllenge
     */
    public String geetestChallenge;

    /**
     * 极验验证二次验证表单数据 validate
     */
    public String geetestValidate;

    /**
     * 极验验证二次验证表单数据 seccode
     */
    public String geetestSeccode;

    /** token */
    public String token;
}
