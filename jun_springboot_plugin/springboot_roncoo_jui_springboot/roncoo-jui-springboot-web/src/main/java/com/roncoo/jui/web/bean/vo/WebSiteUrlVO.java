package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网址汇总地址
 * </p>
 *
 * @author Wujun
 * @since 2017-11-22
 */
@Data
@Accessors(chain = true)
public class WebSiteUrlVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 状态
     */
    private String statusId;
    /**
     * 排序
     */
    private Integer sort;
    private Long siteId;
    /**
     * 标题
     */
    private String urlName;
    /**
     * 描述
     */
    private String urlDesc;
    /**
     * 内网
     */
    private String inNet;
    /**
     * 外网
     */
    private String outNet;



}
