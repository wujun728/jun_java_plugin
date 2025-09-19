package io.github.wujun728.online.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 在线表实体类
 * 用于表示在线表单的表结构信息
 */
@Data
@TableName("online_table")
public class OnlineTableEntity {
    
    /**
     * 表ID
     */
    @TableId("id")
    private String id;
    
    /**
     * 表名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 表备注
     */
    @TableField("comments")
    private String comments;
    
    /**
     * 表单布局
     */
    @TableField("form_layout")
    private Integer formLayout;
    
    /**
     * 树形结构标识
     */
    @TableField("tree")
    private Integer tree;
    
    /**
     * 树形父ID
     */
    @TableField("tree_pid")
    private String treePid;
    
    /**
     * 树形标签
     */
    @TableField("tree_label")
    private String treeLabel;
    
    /**
     * 表类型
     */
    @TableField("table_type")
    private Integer tableType;
    
    /**
     * 创建人
     */
    @TableField("creator")
    private Long creator;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 更新人
     */
    @TableField("updater")
    private Long updater;
    
    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    
    /**
     * 删除标记
     */
    @TableField("deleted")
    private Integer deleted;
}

