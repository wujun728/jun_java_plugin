package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 下载ZIP包时所需IO
 *
 * @author TongWei.Chen 2017-05-18 12:41:59
 */
public class DownZIPIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 所需下载的图片的url集合 */
    @NotEmpty(message = "请选择需要下载的资源")
    @NotNull(message = "请选择需要下载的资源")
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
