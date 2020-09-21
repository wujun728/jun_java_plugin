package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TongWei.Chen 2017-11-30 13:24:54
 * @Project tucaole
 * @Description: 省市区VO
 */
@Data
public class LocationIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;
    /** 名称 */
    private String name;
}
