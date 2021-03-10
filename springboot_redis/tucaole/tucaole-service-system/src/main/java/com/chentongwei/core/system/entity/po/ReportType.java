package com.chentongwei.core.system.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽了举报类型表
 */
@Data
public class ReportType implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 名称 */
    private String name;
    /** 父id，若为0则代表是1级菜单 */
    private Integer pid;
    /** 是否删除： 0：已删除；1：未删除 */
    private Integer isDelete;
    /** 创建时间 */
    private Date createTime;

}