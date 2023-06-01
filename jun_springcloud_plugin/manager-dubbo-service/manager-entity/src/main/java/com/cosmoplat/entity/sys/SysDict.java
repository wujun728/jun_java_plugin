package com.cosmoplat.entity.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author manager
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    /**
     * 标签名
     */
    @NotNull(message = "字典名不能为空")
    private String dictName;

    /**
     * 数据值
     */
    @NotNull(message = "字典值不能为空")
    private String dictValue;

    /**
     * 类型
     */
    private String dictType;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 父级id
     */
    @NotNull(message = "父级编号不能为空")
    private String parentId;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 扩充字段 - 自定义数据格式  String or JSONString
     */
    private String attr;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    private String createId;

    private String updateId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 子集
     */
    @TableField(exist = false)
    private List<?> children;

    /**
     * 父级名称
     */
    @TableField(exist = false)
    private String parentName;

}
