package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * RolePermissionOperationReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class RolePermissionOperationReqVO implements Serializable {
    @NotBlank(message = "角色id不能为空")
    private String roleId;
    @NotEmpty(message = "菜单权限集合不能为空")
    private List<String> permissionIds;
}
