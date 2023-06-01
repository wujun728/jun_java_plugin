package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * UpdatePasswordReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class UpdatePasswordReqVO implements Serializable {
    private String oldPwd;
    private String newPwd;
}
