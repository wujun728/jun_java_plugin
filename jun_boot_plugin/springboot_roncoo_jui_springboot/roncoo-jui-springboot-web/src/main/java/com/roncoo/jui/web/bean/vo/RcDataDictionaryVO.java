package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class RcDataDictionaryVO implements Serializable {

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
     * 字段名
     */
    private String fieldName;
    /**
     * 字段CODE
     */
    private String fieldCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;



}
