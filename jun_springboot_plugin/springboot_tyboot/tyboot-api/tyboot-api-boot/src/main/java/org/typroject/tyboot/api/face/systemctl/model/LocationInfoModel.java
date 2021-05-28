package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

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
public class LocationInfoModel extends BaseModel
{

	private static final long serialVersionUID = 8978734890730924L;

	private String pinyinName;



	private String locationName;

	private String locationCode;


	private String parentCode;

	private Integer locationLevel;

	private Integer orderNum;


	/**
	 * 经度
	 */
	private String longitude;

	//维度

	private String latitude;
}

