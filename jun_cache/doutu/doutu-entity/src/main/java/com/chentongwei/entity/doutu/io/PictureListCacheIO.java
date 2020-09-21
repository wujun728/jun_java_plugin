package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.entity.Page;

import javax.validation.constraints.NotNull;

/**
 * listIO
 *
 * @author TongWei.Chen 2017-05-17 18:34:22
 */
public class PictureListCacheIO extends Page {

	private static final long serialVersionUID = 1L;
	
	/** 分类id */
    @NotNull
    private Integer catalogId;

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }
}
