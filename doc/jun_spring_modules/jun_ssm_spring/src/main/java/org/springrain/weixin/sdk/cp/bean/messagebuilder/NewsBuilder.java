package org.springrain.weixin.sdk.cp.bean.messagebuilder;

import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 *
 * @author springrain
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxCpMessage.WxArticle> articles = new ArrayList<>();

  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxCpMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  @Override
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
