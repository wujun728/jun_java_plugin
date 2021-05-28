package com.chentongwei.core.user.entity.io.favorite;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹删除IO
 **/
@Data
public class ArticleFavoriteDeleteIO extends TokenIO {
    /** 主键 id */
    @NotNull
    private Long id;
}
