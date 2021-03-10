package com.chentongwei.core.tucao.entity.vo.article;

import lombok.Data;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章
 */
@Data
public class ArticleListVO extends ArticleOperateCount {

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
    /** 点赞状态 -1:不高亮；0：反对；1：支持 */
    private Integer supportStatus;
    /** 是否匿名：0：匿名；1：正常 */
    private Integer isAnonymous;
    /** 发表人id */
    private Long userId;
    /** 发表人名称 */
    private String nickname;
    /** 发表人头像 */
    private String avatar;
}