package com.battcn.boot.extend.configuration.oss.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 请求参数
 *
 * @author Levin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageRequest implements java.io.Serializable {

    private static final long serialVersionUID = -3607203589216325639L;


    private String bucket;

    private String fileName;

    /**
     * content 与 inputStream 二选一
     */
    private byte[] content;
    /**
     * inputStream 与 content 二选一
     */
    private InputStream inputStream;

    private String contentType;

}
