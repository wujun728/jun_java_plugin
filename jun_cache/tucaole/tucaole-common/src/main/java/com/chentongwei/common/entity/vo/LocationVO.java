package com.chentongwei.common.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TongWei.Chen 2017-11-30 13:24:54
 * @Project tucaole
 * @Description: 省市区VO
 */
@Data
public class LocationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;
    /** 名称 */
    private String name;

    public LocationVO id(Integer id) {
        this.id = id;
        return this;
    }

    public LocationVO name(String name) {
        this.name = name;
        return this;
    }
}
