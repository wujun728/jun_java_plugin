package com.cosmoplat.entity.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
@Accessors(chain = true)
public class SysRolePermission implements Serializable {
    @TableId
    private String id;

    private String roleId;

    private String permissionId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}