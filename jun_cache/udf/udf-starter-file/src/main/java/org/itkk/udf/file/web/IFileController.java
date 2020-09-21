/**
 * IFileController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.file.FileInfo;
import org.itkk.udf.core.domain.file.FileParam;
import org.itkk.udf.file.FileProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 描述 : IFileController
 *
 * @author Administrator
 */
@Api(value = "文件服务", consumes = "application/json", produces = "application/json",
        protocols = "http")
@RequestMapping(value = "/service/file")
public interface IFileController {

    /**
     * 描述 : 上传文件
     *
     * @param pathCode 路径代码
     * @param file     文件
     * @return 结果
     * @throws IOException 异常
     */
    @ApiOperation(value = "FILE_1", notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "form", name = "pathCode", value = "路径代码", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "form", name = "file", value = "文件", required = true, dataType = "__file")
    })
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    RestResponse<FileInfo> upload(String pathCode, MultipartFile file) throws IOException;

    /**
     * 描述 : 文件配置
     *
     * @return 文件配置
     */
    @ApiOperation(value = "FILE_2", notes = "文件配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string")
    })
    @RequestMapping(value = "properties", method = RequestMethod.GET)
    RestResponse<FileProperties> properties();

    /**
     * 描述 : 通过文件ID访问文件
     *
     * @param fileParam 文件参数
     * @param response  响应对象
     * @throws IOException 异常
     */
    @ApiOperation(value = "FILE_3", notes = "通过文件ID访问文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string")
    })
    @RequestMapping(value = "download", method = RequestMethod.POST)
    void download(@RequestBody FileParam fileParam, HttpServletResponse response) throws IOException;

    /**
     * 描述 : 通过文件ID访问文件
     *
     * @param fileParam 文件参数
     * @return 文件信息
     * @throws IOException 异常
     */
    @ApiOperation(value = "FILE_4", notes = "通过文件ID获得文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string")
    })
    @RequestMapping(value = "info", method = RequestMethod.POST)
    RestResponse<FileInfo> info(@RequestBody FileParam fileParam) throws IOException;

    /**
     * 描述 : 批量上传文件
     *
     * @param pathCode 路径代码
     * @param files    文件清单
     * @return 结果
     * @throws IOException 异常
     */
    @ApiOperation(value = "FILE_5", notes = "批量上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "form", name = "pathCode", value = "路径代码", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "form", name = "files", value = "文件清单", required = true, allowMultiple = true, dataType = "__file")
    })
    @RequestMapping(value = "upload/batch", method = RequestMethod.POST)
    RestResponse<List<FileInfo>> batchUpload(String pathCode, List<MultipartFile> files) throws IOException;

}
