package com.chentongwei.core.tucao.entity.io.support;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞saveIO
 */
@Data
public class ArticleSupportSaveIO extends TokenIO {

    /** 吐槽文章id */
    @NotNull
    private Long articleId;
    /** 0：取消点赞；1：点赞 */
    private Integer status;

}
