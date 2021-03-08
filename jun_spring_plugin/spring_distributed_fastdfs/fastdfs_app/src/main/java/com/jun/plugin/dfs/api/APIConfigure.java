package com.jun.plugin.dfs.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class APIConfigure {

    /**
     * 应用编码
     */
    @NonNull
    private final String appKey;

    /**
     * DFS HTTP服务器的地址
     * http://xxxx.com:8080/
     */
    @NonNull
    private final String httpServerUrl;

    /**
     * 上传缓存大小(非必要,默认1M)
     */
    private int uploadBufferSize;

    /**
     * 下载缓存大小(非必要,默认1M)
     */
    private int downloadBufferSize;

    /**
     * http请求线程池大小(非必要,默认5)
     */
    private int coreThreadSize;
}
