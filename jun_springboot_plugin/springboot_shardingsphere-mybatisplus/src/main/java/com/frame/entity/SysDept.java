package com.frame.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统部门
 * </p>
 *
 * @author 19047921
 * @since 2021-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 部门编号(规则：父级关系编码+自己的编码)
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级id
     */
    private String pid;

    /**
     * 状态(1:正常；0:弃用)
     */
    private Integer status;

    /**
     * 为了维护更深层级关系
     */
    private String relationCode;

    /**
     * 部门经理user_id
     */
    private String deptManagerId;

    /**
     * 部门经理名称
     */
    private String managerName;

    /**
     * 部门经理联系电话
     */
    private String phone;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否删除 1删除 0未删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;



}
