package com.jun.plugin.resources;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源接口
 * Created by Hong on 2017/12/26.
 */
public interface ReadResources {

    /**
     * 加载资源
     * @param is            输入流
     * @throws IOException  异常
     */
    void load(InputStream is) throws IOException;
}