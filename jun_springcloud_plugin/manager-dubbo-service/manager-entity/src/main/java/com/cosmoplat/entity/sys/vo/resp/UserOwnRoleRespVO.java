package com.cosmoplat.entity.sys.vo.resp;

import com.cosmoplat.entity.sys.SysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * UserOwnRoleRespVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class UserOwnRoleRespVO implements Serializable {
    private List<SysRole> allRole;
    private List<String> ownRoles;
}
