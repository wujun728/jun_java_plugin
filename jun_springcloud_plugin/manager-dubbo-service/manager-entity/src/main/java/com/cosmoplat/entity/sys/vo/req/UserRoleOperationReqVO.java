package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * UserRoleOperationReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class UserRoleOperationReqVO implements Serializable {
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @NotEmpty(message = "角色id集合不能为空")
    private List<String> roleIds;
}
