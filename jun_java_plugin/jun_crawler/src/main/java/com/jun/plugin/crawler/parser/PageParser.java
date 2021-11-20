package com.jun.plugin.crawler.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jun.plugin.crawler.model.PageRequest;

/**
 * page parser
 *
 * @author xuxueli 2017-10-17 18:50:40
 *
 * @param <T>   PageVo
 */
public abstract class PageParser<T> {

    /**
     * pre parse page, before page load
     *
     * @param pageRequest  page request params
     */
    public void preParse(PageRequest pageRequest) {
        // TODO
    }

    /**
     * parse pageVo
     *
     * @param html              page html data
     * @param pageVoElement     pageVo html data
     * @param pageVo            pageVo object
     */
    public abstract void parse(Document html, Element pageVoElement, T pageVo);

}
