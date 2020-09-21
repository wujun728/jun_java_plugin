package com.chentongwei.entity.doutu.io;

import java.io.Serializable;

/**
 * 图片分类IO
 *
 * @author TongWei.Chen 2017-05-17 10:19:37
 */
public class CatalogIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 分类名称 */
    private String name;

    public CatalogIO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CatalogIO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
