package com.chentongwei.core.user.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 关注表
 */
@Data
public class Follow implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 关注者id */
    private Long userId;
    /** 被关注者id */
    private Long followUserId;
    /** 关注时间 */
    private Date createTime;
}
