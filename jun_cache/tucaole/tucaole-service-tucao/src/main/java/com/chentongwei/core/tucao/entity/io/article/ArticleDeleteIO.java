package com.chentongwei.core.tucao.entity.io.article;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author TongWei.Chen 2017-12-12 18:57:00
 * @Project tucaole
 * @Description: 吐槽文章删除IO
 */
@Data
public class ArticleDeleteIO extends TokenIO {

    /** 主键 id */
    @NotNull
    private Long id;
}