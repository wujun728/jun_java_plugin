package com.chentongwei.core.user.entity.vo.follow;

import lombok.Data;

/**
 * @author TongWei.Chen 2017-12-20 19:10:42
 * @Project tucaole
 * @Description: 我关注的、关注我的人
 */
@Data
public class FollowListVO {
    /** id */
    private Long id;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
}
