package com.jun.plugin.crawler.test.util;

import java.net.InetSocketAddress;
import java.net.Proxy;

import com.jun.plugin.crawler.util.ProxyIpUtil;

/**
 * proxy ip util test
 *
 * @author xuxueli 2017-11-08 13:35:16
 */
public class ProxyIpUtilTest {

    public static void main(String[] args) {
        int code = ProxyIpUtil.checkProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("---", 80)), null);
        System.out.println(code);
    }

}
