package com.chentongwei.core.user.entity.io.favorite;

import com.chentongwei.common.entity.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹列表IO
 **/
@Data
public class ArticleFavoriteListIO extends Page {

    /** 用户Id */
    @NotNull
    private Long userId;
}
