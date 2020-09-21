package com.chentongwei.core.user.entity.io.follow;

import com.chentongwei.common.entity.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author TongWei.Chen 2017-12-20 19:16:05
 * @Project tucaole
 * @Description: 我关注的人、关注我的人列表
 */
@Data
public class FollowListIO extends Page {
    /** 被关注者id */
    @NotNull
    private Long userId;
}
