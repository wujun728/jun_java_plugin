package com.chentongwei.core.tucao.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽分类
 */
@Data
public class ArticleCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 分类名称 */
    private String name;
    /** pid */
    private Integer pid;
    /** 是否删除： 0：已删除；1：未删除 */
    private Integer isDelete;
    /** 创建时间 */
    private Date createTime;

}
