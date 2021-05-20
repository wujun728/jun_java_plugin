package org.typroject.tyboot.api.controller.systemctl;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.typroject.tyboot.component.opendata.storage.Storage;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.foundation.utils.StringUtil;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: StorageResourceV1.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 * 
 *  Notes:
 *  $Id: StorageResourceV1.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */

@RestController
@RequestMapping(value = "/v1/systemctl/storage")
@TycloudResource(module = "systemctl",value = "storage", description = "存储服务")
@Api(tags = "systemctl-存储服务")
public class StorageResource {

	//@Autowired
	private Storage storage;

	//@Value("${qiniu.bucket}")
	private String qiniuBucket;

	private static Set<String> spaceNames;
	
	@TycloudOperation( ApiLevel = UserType.ANONYMOUS)
	@ApiOperation(value="根据空间获取七牛token")
	@RequestMapping(value = "/token/{space}", method = RequestMethod.GET)
	public ResponseModel<String> flushQiniuToken(@PathVariable String space) throws Exception {
		return ResponseHelper.buildResponse(storage.flushQiniuToken(this.checkSpaceName(space)));
	}


	@TycloudOperation( ApiLevel = UserType.ANONYMOUS)
	@ApiOperation(value="获取图片空间accessToken")
	@RequestMapping(value = "/token/zoulu/image", method = RequestMethod.GET)
	public ResponseModel<String> flushQiniuToken() throws Exception {
		return ResponseHelper.buildResponse(storage.flushQiniuToken(qiniuBucket));
	}


	
	@TycloudOperation( ApiLevel = UserType.ANONYMOUS)
	@RequestMapping(value = "/{space}", method = RequestMethod.DELETE)
	public ResponseModel<String> deleteFile(
			@PathVariable String space,
			@RequestParam(value ="fileName") String fileName) throws Exception {
		storage.deleteFile(this.checkSpaceName(space), fileName);
		return ResponseHelper.buildResponse("SUCCEED");
	}


	private String checkSpaceName(String spaceName) throws Exception
	{
		if(ValidationUtil.isEmpty(qiniuBucket))
			throw new Exception("空间名称有误.");

		if(ValidationUtil.isEmpty(spaceNames))
		{
			spaceNames = new HashSet<>();
			String [] names = StringUtil.string2Array(qiniuBucket);
			for(String name:names)
			{
				spaceNames.add(name);
			}

		}

		if(!spaceNames.contains(spaceName))
		{
			throw new Exception("空间名称有误.");
		}
		return spaceName;
	}


}
