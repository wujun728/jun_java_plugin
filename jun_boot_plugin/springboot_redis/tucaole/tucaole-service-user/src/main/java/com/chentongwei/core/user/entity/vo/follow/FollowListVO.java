package com.chentongwei.core.user.entity.vo.follow;

import lombok.Data;

/**
 * @author Wujun
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
