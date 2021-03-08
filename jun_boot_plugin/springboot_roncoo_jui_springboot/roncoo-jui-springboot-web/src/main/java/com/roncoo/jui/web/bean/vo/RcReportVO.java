package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报表
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class RcReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 状态
     */
    private String statusId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户QQ
     */
    private String userEmail;
    /**
     * 用户昵称
     */
    private String userNickname;



}
