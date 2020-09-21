/**
 * SchClientService.java
 * Created at 2017-06-12
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client.service;

import org.itkk.udf.core.RestResponse;
import org.itkk.udf.rms.Rms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 : SchClientService
 *
 * @author Administrator
 */
@Service
public class SchClientService {

    /**
     * 描述 : rms
     */
    @Autowired
    private Rms rms;

    /**
     * 描述 : 触发作业
     *
     * @param jobCode 作业代码
     * @param param 参数
     * @return id
     */
    public String trigger(String jobCode, Map<String, String> param) {
        //接口编号
        final String serviceCode = "SCHEDULER_JOB_4";
        //rest参数
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("jobCode", jobCode);
        //请求
        ResponseEntity<RestResponse<String>> result = rms.call(serviceCode, param, null, new ParameterizedTypeReference<RestResponse<String>>() {
        }, uriVariables);
        //返回
        return result.getBody().getResult();
    }

}
