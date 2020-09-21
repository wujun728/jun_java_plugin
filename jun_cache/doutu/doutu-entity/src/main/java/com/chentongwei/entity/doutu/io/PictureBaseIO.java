package com.chentongwei.entity.doutu.io;

import java.io.Serializable;

/**
 * TODO(这里用一句话描述这个类的作用)
 *
 * @author TongWei.Chen 2017-06-14 10:47:06
 */
public class PictureBaseIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Hash值，验证图片是否存在 */
    private String hash;
    /** 图片路径 */
    private String url;
    /** 图片字节数组，验证图片是否已经存在时候使用 */
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
