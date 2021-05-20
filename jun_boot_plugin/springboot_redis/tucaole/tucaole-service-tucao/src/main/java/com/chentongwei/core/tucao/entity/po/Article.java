package com.chentongwei.core.tucao.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章
 */
@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 id */
    private Integer id;
    /** 分类id */
    private Integer catalogId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 内容app端用【JSON格式】 */
    private String contentApp;
    /** 阅读数 */
    private Integer readCount;
    /** 是否匿名：0：匿名；1：正常 */
    private Integer isAnonymous;
    /** 是否删除： 0：已删除；1：未删除 */
    private Integer isDelete;
    /** 创建人id */
    private Integer creatorId;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;
}