package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: LocationInfo.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: LocationInfo.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("systemctl_location_info")
public class LocationInfo extends BaseEntity
{
	private static final long serialVersionUID = 4156498435169789L;

	@TableField("PINYIN_NAME")
	private String pinyinName;



	@TableField("LOCATION_NAME")
	private String locationName;

	@TableField("LOCATION_CODE")
	private String locationCode;


	@TableField("PARENT_CODE")
	private String parentCode;

	@TableField("LOCATION_LEVEL")
	private Integer locationLevel;

	@TableField( "ORDER_NUM")
	private Integer orderNum;


	/**
	 * 经度
	 */
	@TableField( "LONGITUDE")
	private String longitude;

	//维度

	@TableField( "LATITUDE")
	private String latitude;
}

