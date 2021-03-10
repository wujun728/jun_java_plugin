package com.chentongwei.core.tucao.entity.vo.article;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章的操作数，比如：点赞数、评论数、点踩数等等
 */
@Data
public class ArticleOperateCount implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 id */
    private Long id;
    /** 评论数 */
    private Long commentCount;
    /** 支持数（点赞数） */
    private Long supportCount;
    /** 反对数 */
    private Long opposeCount;
}
