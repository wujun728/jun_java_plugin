package com.jun.plugin.dfs.listener;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class InitializeConfig {

    /**
     * 上传线程数
     */
    @Value("${dfs.thread.num.upload:3}")
    private int upload;

    /**
     * 下载线程数
     */
    @Value("${dfs.thread.num.download:3}")
    private int download;

    /**
     * tracker服务器列表
     */
    @Value("${fastdfs.tracker_servers:192.168.0.4:22122}")
    private String trackers;

    /**
     * 连接超时
     */
    @Value("${fastdfs.connect_timeout_in_seconds:5}")
    private String connectTimeoutInSeconds;

    /**
     * 网络超时
     */
    @Value("${fastdfs.network_timeout_in_seconds:30}")
    private String networkTimeoutInSeconds;

    /**
     * 字符编码
     */
    @Value("${fastdfs.charset:utf-8}")
    private String charset;
}
