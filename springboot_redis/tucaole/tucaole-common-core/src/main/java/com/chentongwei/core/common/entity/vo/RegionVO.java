package com.chentongwei.core.common.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区VO
 */
@Data
public class RegionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 名称 */
    private String name;
    /** 父级关系 */
    private Integer pid;
    /** 子节点 */
    private List<RegionVO> children;
}
