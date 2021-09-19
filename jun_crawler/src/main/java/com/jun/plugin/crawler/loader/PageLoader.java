package com.jun.plugin.crawler.loader;

import org.jsoup.nodes.Document;

import com.jun.plugin.crawler.model.PageRequest;

/**
 * page loader
 *
 * @author xuxueli 2017-12-28 00:27:30
 */
public abstract class PageLoader {

    /**
     * load page
     *
     * @param pageRequest
     * @return Document
     */
    public abstract Document load(PageRequest pageRequest);

}
