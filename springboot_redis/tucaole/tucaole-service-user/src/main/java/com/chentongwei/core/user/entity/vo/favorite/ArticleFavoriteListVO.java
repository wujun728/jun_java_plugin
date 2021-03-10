package com.chentongwei.core.user.entity.vo.favorite;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹列表
 **/
@Data
public class ArticleFavoriteListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 收藏夹名称 */
    private String name;
    /** 是否私有：0：私有；1：公开 */
    private Integer isPrivate;
    /** 创建时间 */
    private Date createTime;
    /** 资源总数 */
    private Long count;
}
