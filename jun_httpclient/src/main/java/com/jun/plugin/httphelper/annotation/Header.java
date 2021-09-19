package com.jun.plugin.httphelper.annotation;

public @interface Header {
    /**
     * 名称
     *
     * @return
     */
    public String name();

    /**
     * 值
     *
     * @return
     */
    public String value();
}
