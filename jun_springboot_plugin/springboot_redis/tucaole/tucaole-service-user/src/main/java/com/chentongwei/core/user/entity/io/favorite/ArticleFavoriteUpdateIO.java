package com.chentongwei.core.user.entity.io.favorite;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 保存收藏夹IO
 **/
@Data
public class ArticleFavoriteUpdateIO extends TokenIO {

    /** 主键id */
    @NotNull
    private Long id;
    /** 收藏夹名称 */
    @NotNull
    @NotBlank
    private String name;
    /** 收藏夹描述 */
    private String remark;
    /** 是否私有：0：私有；1：公开 */
    @NotNull
    private Integer isPrivate;
}
