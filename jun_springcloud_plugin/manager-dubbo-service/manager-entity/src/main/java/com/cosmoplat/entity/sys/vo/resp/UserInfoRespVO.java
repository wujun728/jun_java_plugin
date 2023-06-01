package com.cosmoplat.entity.sys.vo.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * UserInfoRespVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class UserInfoRespVO implements Serializable {
    private String id;
    private String username;
    private String phone;
    private String nickName;
    private String realName;
    private String deptId;
    private String deptName;

}
