package org.itkk.udf.id.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * CacheValue
 */
@Data
public class CacheValue implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * host
     */
    private String host;

    /**
     * 端口号
     */
    private int port;
}
