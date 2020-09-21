package com.chentongwei.core.tucao.entity.vo.comment;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TongWei.Chen 2017-12-22 13:26:07
 * @Project tucaole
 * @Description: 评论/回复用户VO
 */
@Data
public class ArticleCommentUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;
    /** 头像 */
    private String avatar;
    /** 登录名(昵称) */
    private String nickname;
    /** 介绍 */
    private String introduce;
}
