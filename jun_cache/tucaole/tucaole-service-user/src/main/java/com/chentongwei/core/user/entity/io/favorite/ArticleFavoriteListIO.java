package com.chentongwei.core.user.entity.io.favorite;

import com.chentongwei.common.entity.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author TongWei.Chen 2017-12-17 14:17:00
 * @Project tucaole
 * @Description: 收藏夹列表IO
 **/
@Data
public class ArticleFavoriteListIO extends Page {

    /** 用户Id */
    @NotNull
    private Long userId;
}
