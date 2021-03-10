package com.chentongwei.core.user.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹
 */
@Data
public class ArticleFavorite implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 收藏夹名称 */
    private String name;
    /** 收藏夹描述 */
    private String remark;
    /** 是否私有：0：私有；1：公开 */
    private Integer isPrivate;
    /** 是否删除： 0：已删除；1：未删除 */
    private Integer isDelete;
    /** 创建人id */
    private Long creatorId;
    /** 创建时间 */
    private Date createTime;
}