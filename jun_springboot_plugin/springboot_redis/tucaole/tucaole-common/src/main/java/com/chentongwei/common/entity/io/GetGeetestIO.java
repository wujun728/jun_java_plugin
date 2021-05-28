package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 极验验证码IO
 **/
@Data
public class GetGeetestIO implements Serializable {
    private static final long serialVersionUID = 1L;
    //验证码信息
    private String challenge;
    private String gt;
    private String success;
    //token
    private String token;
}
