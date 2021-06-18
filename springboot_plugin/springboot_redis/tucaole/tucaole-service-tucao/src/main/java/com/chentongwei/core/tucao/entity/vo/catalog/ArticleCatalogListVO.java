package com.chentongwei.core.tucao.entity.vo.catalog;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽分类
 */
@Data
public class ArticleCatalogListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 分类名称 */
    private String name;

}
