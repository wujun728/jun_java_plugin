package com.chentongwei.core.system.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽了举报
 */
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 举报资源id */
    private Long resourceId;
    /** 举报者id */
    private Long userId;
    /** 举报原因 */
    private String remark;
    /** 反馈说明 */
    private String feedback;
    /** 审核状态;1：待审核； 2：已通过;3:已拒绝 */
    private Integer status;
    /** 举报类型表id */
    private Integer reportTypeId;
    /** 类型：1：吐槽的文章；2：吐槽文章的评论；*/
    private Integer type;
    /** 创建时间 */
    private Date createTime;
}