package com.chentongwei.core.tucao.entity.io.article;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章删除IO
 */
@Data
public class ArticleDeleteIO extends TokenIO {

    /** 主键 id */
    @NotNull
    private Long id;
}