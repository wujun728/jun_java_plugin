package com.chentongwei.core.user.entity.vo.favorite;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹详情
 */
@Data
public class ArticleFavoriteDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 收藏夹名称 */
    private String name;
    /** 收藏夹描述 */
    private String remark;
    /** 是否私有：0：私有；1：公开 */
    private Integer isPrivate;
    /** 创建时间 */
    private Date createTime;
    /** TODO 收藏文章集合 */
    private List<?> list;
}
