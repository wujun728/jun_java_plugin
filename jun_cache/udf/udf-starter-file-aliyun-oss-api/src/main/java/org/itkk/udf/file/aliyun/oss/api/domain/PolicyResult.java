package org.itkk.udf.file.aliyun.oss.api.domain;

import lombok.Data;
import org.itkk.udf.file.aliyun.oss.api.meta.AliyunOssPathMeta;

import java.io.Serializable;

/**
 * PolicyResult
 */
@Data
public class PolicyResult implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * accessId
     */
    private String accessId;

    /**
     * policy
     */
    private String policy;

    /**
     * signature
     */
    private String signature;

    /**
     * expire
     */
    private String expire;

    /**
     * aliyunOssPathProperties
     */
    private AliyunOssPathMeta aliyunOssPathMeta;
}
