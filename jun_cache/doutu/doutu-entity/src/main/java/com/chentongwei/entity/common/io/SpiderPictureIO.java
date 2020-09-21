package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 爬虫获取图片的IO
 *
 * @author TongWei.Chen 2017-06-21 13:05:58
 */
public class SpiderPictureIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 第几页 */
    @NotNull
    private int page;
    /** 分类名 */
    @NotNull
    @NotEmpty
    @NotBlank
    private String catalogName;
    /** 分类id */
    @NotNull
    private Integer catalogId;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }
}
