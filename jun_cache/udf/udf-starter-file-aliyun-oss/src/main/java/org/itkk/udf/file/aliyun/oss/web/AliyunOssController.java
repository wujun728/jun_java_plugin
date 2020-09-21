/**
 * FileController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.web;

import com.aliyun.oss.model.OSSObjectSummary;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.aliyun.oss.OssListParam;
import org.itkk.udf.core.domain.aliyun.oss.OssParam;
import org.itkk.udf.file.aliyun.oss.api.OssWarpper;
import org.itkk.udf.file.aliyun.oss.api.domain.PolicyResult;
import org.itkk.udf.file.aliyun.oss.api.download.DownLoadProcessService;
import org.itkk.udf.file.aliyun.oss.api.download.DownloadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 描述 : AliyunOssController
 *
 * @author Administrator
 */
@RestController
public class AliyunOssController implements IAliuyinOssController {

    /**
     * ossWarpper
     */
    @Autowired
    private OssWarpper ossWarpper;

    /**
     * downLoadProcessService
     */
    @Autowired
    private DownLoadProcessService downLoadProcessService;

    @Override
    public RestResponse<List<OSSObjectSummary>> list(@RequestBody @Valid OssListParam ossListParam) {
        return new RestResponse<>(ossWarpper.list(ossListParam.getCode(), ossListParam.getKeyPrefix()));
    }

    @Override
    public RestResponse<PolicyResult> policy(@PathVariable String code) throws IOException {
        return new RestResponse<>(ossWarpper.getPolicy(code));
    }

    @Override
    public RestResponse<String> presignedUrl(@RequestBody @Valid OssParam ossParam) {
        return new RestResponse<>(ossWarpper.getPresignedUrl(ossParam.getCode(), ossParam.getObjectKey()));
    }

    @Override
    public RestResponse<String> delete(@RequestBody @Valid OssParam ossParam) {
        ossWarpper.delete(ossParam.getCode(), ossParam.getObjectKey());
        return new RestResponse<>();
    }

    @Override
    public RestResponse<Boolean> checkExist(@RequestBody @Valid OssParam ossParam) {
        return new RestResponse<>(ossWarpper.checkExist(ossParam.getCode(), ossParam.getObjectKey()));
    }

    @Override
    public RestResponse<DownloadInfo> getDownloadInfo(@PathVariable String id) {
        return new RestResponse<>(downLoadProcessService.getDownloadInfo(id));
    }
}
