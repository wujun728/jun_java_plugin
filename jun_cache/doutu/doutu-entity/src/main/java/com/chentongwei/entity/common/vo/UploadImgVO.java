package com.chentongwei.entity.common.vo;

import java.io.Serializable;

/**
 * 图片上传VO
 *
 * @author TongWei.Chen 2017-06-13 20:22:00
 */
public class UploadImgVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 图片hash值 */
    private String hash;
    /** 图片url */
    private String url;
    /** 图片字节数组 */
    private byte[] bytes;
    /** 文件名后缀 */
    private String suffix;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
