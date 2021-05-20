package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网址汇总
 * </p>
 *
 * @author Wujun
 * @since 2017-11-22
 */
@Data
@Accessors(chain = true)
public class WebSiteVO implements Serializable {

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
	/**
	 * 标题
	 */
	private String title;
	/**
	 * LOGO
	 */
	private String siteLogo;
	/**
	 * 名字
	 */
	private String siteName;
	/**
	 * 描述
	 */
	private String siteDesc;
	/**
	 * URL
	 */
	private String siteUrl;

	private List<WebSiteUrlVO> list;

}
