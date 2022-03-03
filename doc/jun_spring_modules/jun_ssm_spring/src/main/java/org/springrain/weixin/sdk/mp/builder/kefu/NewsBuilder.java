package org.springrain.weixin.sdk.mp.builder.kefu;

import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.mp.bean.kefu.WxMpKefuMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author springrain
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxMpKefuMessage.WxArticle> articles = new ArrayList<>();

  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxMpKefuMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  @Override
  public WxMpKefuMessage build() {
    WxMpKefuMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
