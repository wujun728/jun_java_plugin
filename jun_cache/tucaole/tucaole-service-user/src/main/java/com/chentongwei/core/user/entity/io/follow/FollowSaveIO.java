package com.chentongwei.core.user.entity.io.follow;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author TongWei.Chen 2017-12-20 12:39:16
 * @Project tucaole
 * @Description: 保存关注记录
 */
@Data
public class FollowSaveIO extends TokenIO {

    /** 主键id */
    private Long id;
    /** 被关注者id */
    @NotNull
    private Long followUserId;
}
