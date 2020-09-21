/**
 * IIdWorkerController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.id.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itkk.udf.core.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 描述 : IIdWorkerController
 *
 * @author Administrator
 */
@Api(value = "分布式ID服务", consumes = "application/json", produces = "application/json", protocols = "http")
@RequestMapping(value = "/service/id")
public interface IIdWorkerController { //NOSONAR

    /**
     * 获得分布式ID
     *
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_1", notes = "获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string")
    })
    @RequestMapping(method = RequestMethod.GET)
    RestResponse<String> nextId();

    /**
     * 获得分布式ID
     *
     * @param dwId 数据中心ID | 机器ID ( 0 - 1023 )
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_2", notes = "获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "dwId", value = "数据中心ID | 机器ID ( 0 - 1023 )", required = true, dataType = "int")
    })
    @RequestMapping(value = "{dwId}", method = RequestMethod.GET)
    RestResponse<String> nextId(@PathVariable Integer dwId);

    /**
     * 获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_3", notes = "获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "datacenterId", value = "数据中心ID ( 0 - 31 )", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "workerId", value = "机器ID ( 0 - 31 )", required = true, dataType = "int")
    })
    @RequestMapping(value = "{datacenterId}/{workerId}", method = RequestMethod.GET)
    RestResponse<String> nextId(@PathVariable Integer datacenterId, @PathVariable Integer workerId);

    /**
     * 批量获得分布式ID
     *
     * @param count count
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_4", notes = "批量获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "count", value = "数量", required = true, dataType = "int")
    })
    @RequestMapping(value = "batch/{count}", method = RequestMethod.GET)
    RestResponse<List<String>> batchNextId(@PathVariable Integer count);

    /**
     * 批量获得分布式ID
     *
     * @param dwId  数据中心ID | 机器ID ( 0 - 1023 )
     * @param count count
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_5", notes = "批量获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "dwId", value = "数据中心ID | 机器ID ( 0 - 1023 )", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "count", value = "数量", required = true, dataType = "int")
    })
    @RequestMapping(value = "batch/{dwId}/{count}", method = RequestMethod.GET)
    RestResponse<List<String>> batchNextId(@PathVariable Integer dwId, @PathVariable Integer count);

    /**
     * 批量获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @param count        count
     * @return 分布式ID
     */
    @ApiOperation(value = "ID_6", notes = "批量获得分布式ID (注意:ID为长整型,在javascript下会丢失精度,请注意数据类型转换)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "datacenterId", value = "数据中心ID ( 0 - 31 )", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "workerId", value = "机器ID ( 0 - 31 )", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "count", value = "数量", required = true, dataType = "int")
    })
    @RequestMapping(value = "batch/{datacenterId}/{workerId}/{count}", method = RequestMethod.GET)
    RestResponse<List<String>> batchNextId(@PathVariable Integer datacenterId, @PathVariable Integer workerId, @PathVariable Integer count);

}
