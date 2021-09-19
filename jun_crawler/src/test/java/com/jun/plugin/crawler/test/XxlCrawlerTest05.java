package com.jun.plugin.crawler.test;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.crawler.XxlCrawler;
import com.jun.plugin.crawler.annotation.PageFieldSelect;
import com.jun.plugin.crawler.annotation.PageSelect;
import com.jun.plugin.crawler.conf.XxlCrawlerConf;
import com.jun.plugin.crawler.model.PageRequest;
import com.jun.plugin.crawler.parser.PageParser;
import com.jun.plugin.crawler.util.JsoupUtil;
import com.jun.plugin.crawler.util.ProxyIpUtil;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

/**
 * 爬虫示例05：爬取公开的免费代理，生成动态代理池
 * (免费代理可从搜索获取，免费代理不稳定可以多试几个；仅供学习测试使用，如有侵犯请联系删除； )
 *
 * @author xuxueli 2017-10-09 19:48:48
 */
public class XxlCrawlerTest05 {
    private static Logger logger = LoggerFactory.getLogger(XxlCrawlerTest05.class);

    @PageSelect(cssQuery = "#list > table > tbody > tr")
    public static class PageVo {

        @PageFieldSelect(cssQuery = "td:eq(0)", selectType = XxlCrawlerConf.SelectType.TEXT)
        private String ip;

        @PageFieldSelect(cssQuery = "td:eq(1)", selectType = XxlCrawlerConf.SelectType.TEXT)
        private int port;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public String toString() {
            return "PageVo{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    '}';
        }
    }

    public static void main(String[] args) {

        // 代理池
        final List<PageVo> proxyPool = new ArrayList<PageVo>();

        // 构造爬虫
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://www.kuaidaili.com/free/inha/1/")
                .setWhiteUrlRegexs("https://www.kuaidaili.com/free/inha/\\b[1-2]/")      // 前2页数据
                //.setWhiteUrlRegexs(new HashSet<String>(Arrays.asList("https://www.kuaidaili.com/free/inha/\\\\d+/")))      // 全部数据
                .setThreadCount(5)
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        if (pageVo.getPort() == 0) {
                            return;
                        }

                        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pageVo.getIp(), pageVo.getPort()));
                        if (ProxyIpUtil.checkProxy(proxy, null) == 200) {
                            proxyPool.add(pageVo);
                            logger.info("proxy pool size : {}, new proxy: {}", proxyPool.size(), pageVo);
                        }

                    }
                })
                .build();

        // 启动
        crawler.start(true);

        // 代理池数据
        logger.info("----------- proxy pool total size : {} -----------", proxyPool.size());
        logger.info(proxyPool.toString());

        // 校验代理池    (代理方式请求IP地址查询网IP138，可从页面响应确认代理是否生效)
        logger.info("----------- proxy pool check -----------");
        if (proxyPool!=null && proxyPool.size()>0) {
            for (PageVo pageVo: proxyPool) {
                try {
                    Document html = JsoupUtil.load(new PageRequest("http://2018.ip138.com/ic.asp",
                            null,
                            null,
                            null,
                            XxlCrawlerConf.USER_AGENT_CHROME,
                            null,
                            false,
                            XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT,
                            false,
                            new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pageVo.getIp(), pageVo.getPort()))));
                    logger.info(pageVo + " : " + html.html());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
