package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;

/**
 * 图片分类VO
 *
 * @author TongWei.Chen 2017-05-17 13:51:04
 */
public class CatalogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 分类名称 */
    private String name;

    public CatalogVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CatalogVO() {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CatalogVO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
