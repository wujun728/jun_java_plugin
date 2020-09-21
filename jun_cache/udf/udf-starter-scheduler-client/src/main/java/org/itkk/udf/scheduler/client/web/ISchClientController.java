/**
 * ISchClientController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.scheduler.client.domain.RmsJobParam;
import org.itkk.udf.scheduler.client.domain.RmsJobResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 描述 : ISchClientController
 *
 * @author Administrator
 */
@Api(value = "计划任务客户端", consumes = "application/json", produces = "application/json",
        protocols = "http")
@RequestMapping(value = "/service/scheduler/client")
public interface ISchClientController { //NOSONAR

    /**
     * 描述 : 执行计划任务
     *
     * @param param param
     * @return 执行计划任务
     */
    @ApiOperation(value = "接口编号自定义", notes = "执行计划任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称",
                    required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true,
                    dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号",
                    required = true, dataType = "string")})
    @RequestMapping(method = RequestMethod.POST)
    RestResponse<RmsJobResult> execute(@RequestBody RmsJobParam param);

}
