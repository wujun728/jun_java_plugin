/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.common.consts;

import com.redis.proxy.common.utils.IpUtil;

/**
 * 代理服务常量配置
 *
 * @author zhanggaofeng
 */
public class CacheConsts {

        /**
         * 服务端口
         */
        public static int SERVER_PORT = IpUtil.getAvailablePort(6701);
        public static String SERVER_GROUP = "default";
        public static int SERVER_MIN_THREADS = 50;
        public static int SERVER_MAX_THREADS = 200;
}
