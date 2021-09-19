package com.jun.plugin.crawler.parser.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jun.plugin.crawler.parser.PageParser;

/**
 * non page parser
 *
 * @author xuxueli 2018-10-17
 */
public abstract class NonPageParser extends PageParser {

    @Override
    public void parse(Document html, Element pageVoElement, Object pageVo) {
        // TODO，not parse page, output page source
    }

    /**
     * @param url
     * @param pageSource
     */
    public abstract void parse(String url, String pageSource);

}
