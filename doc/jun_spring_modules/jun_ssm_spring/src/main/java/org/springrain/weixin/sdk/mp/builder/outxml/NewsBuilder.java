package org.springrain.weixin.sdk.mp.builder.outxml;

import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlOutNewsMessage;

/**
 * 图文消息builder
 * @author springrain
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxMpXmlOutNewsMessage> {

  protected final List<WxMpXmlOutNewsMessage.Item> articles = new ArrayList<>();

  public NewsBuilder addArticle(WxMpXmlOutNewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }

  @Override
  public WxMpXmlOutNewsMessage build() {
    WxMpXmlOutNewsMessage m = new WxMpXmlOutNewsMessage();
    for(WxMpXmlOutNewsMessage.Item item : this.articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }

}
