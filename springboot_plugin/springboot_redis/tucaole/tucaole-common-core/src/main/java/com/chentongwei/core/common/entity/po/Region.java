package com.chentongwei.core.common.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区
 */
@Data
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 身份证前六位 */
    private String idcard;
    /** 名称 */
    private String name;
    /** 父级关系 */
    private Integer pid;
    /** 名称拼音 */
    private String englishName;

}