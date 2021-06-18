package com.roncoo.jui.web.bean.qo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典明细表
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class RcDataDictionaryListQO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
     * 字段CODE
     */
    private String fieldCode;
    /**
     * 字段KEY
     */
    private String fieldKey;
    /**
     * 字段VALUE
     */
    private String fieldValue;
    /**
     * 字段排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
}
