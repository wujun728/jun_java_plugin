package com.chentongwei.core.user.entity.io.follow;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
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
