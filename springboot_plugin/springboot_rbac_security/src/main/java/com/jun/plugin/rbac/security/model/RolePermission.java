package com.jun.plugin.rbac.security.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jun.plugin.rbac.security.model.unionkey.RolePermissionKey;

/**
 * <p>
 * 角色-权限
 * </p>
 *
 * @package: com.xkcoding.rbac.security.model
 * @description: 角色-权限
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 13:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Entity
@Table(name = "sec_role_permission")
public class RolePermission {
    /**
     * 主键
     */
    @EmbeddedId
    private RolePermissionKey id;
}
