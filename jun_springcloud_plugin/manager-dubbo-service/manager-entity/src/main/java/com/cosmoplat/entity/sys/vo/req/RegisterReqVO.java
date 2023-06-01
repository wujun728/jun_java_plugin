package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * RegisterReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class RegisterReqVO implements Serializable {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String phone;
    private String realName;
    private String nickName;
    private String email;
    private Integer sex;
    private Integer createWhere;
    @NotBlank(message = "所属机构不能为空")
    private String deptId;

}
