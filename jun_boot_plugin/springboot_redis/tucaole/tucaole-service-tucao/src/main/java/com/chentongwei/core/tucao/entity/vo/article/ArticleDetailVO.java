package com.chentongwei.core.tucao.entity.vo.article;

import lombok.Data;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章详情
 */
@Data
public class ArticleDetailVO extends ArticleOperateCount {

    /** 用户 id */
    private Long creatorId;
    /** 分类id */
    private Integer catalogId;
    /** 分类名称 */
    private String catalogName;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 内容app端用【JSON格式】 */
    private String contentApp;
    /** 阅读数 */
    private Integer readCount;
    /** 是否匿名：0：匿名；1：正常 */
    private Integer isAnonymous;
    /** 点赞状态 -1:不高亮；0：反对；1：支持 */
    private Integer supportStatus;
    /** 关注者人数 */
    private Long followCount;
}
