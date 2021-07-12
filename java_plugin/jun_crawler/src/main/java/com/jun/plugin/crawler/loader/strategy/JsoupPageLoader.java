package com.jun.plugin.crawler.loader.strategy;

import org.jsoup.nodes.Document;

import com.jun.plugin.crawler.loader.PageLoader;
import com.jun.plugin.crawler.model.PageRequest;
import com.jun.plugin.crawler.util.JsoupUtil;

/**
 * jsoup page loader
 *
 * @author xuxueli 2017-12-28 00:29:49
 */
public class JsoupPageLoader extends PageLoader {

    @Override
    public Document load(PageRequest pageRequest) {
        return JsoupUtil.load(pageRequest);
    }

}
