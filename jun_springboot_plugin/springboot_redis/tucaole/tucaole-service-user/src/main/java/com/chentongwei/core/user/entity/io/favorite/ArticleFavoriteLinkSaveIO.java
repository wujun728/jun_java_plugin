package com.chentongwei.core.user.entity.io.favorite;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 保存到收藏夹
 */
@Data
public class ArticleFavoriteLinkSaveIO extends TokenIO {

    /**
     * 文章id
     */
    @NotNull
    private Long articleId;
    /**
     * 收藏夹id
     */
    @NotNull
    private Long favoriteId;
}
