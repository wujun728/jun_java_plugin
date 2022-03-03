package org.springrain.weixin.sdk.cp.bean.outxmlbuilder;

import org.springrain.weixin.sdk.cp.bean.WxCpXmlOutTextMessage;

/**
 * 文本消息builder
 *
 * @author springrain
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxCpXmlOutTextMessage> {
  private String content;

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxCpXmlOutTextMessage build() {
    WxCpXmlOutTextMessage m = new WxCpXmlOutTextMessage();
    setCommon(m);
    m.setContent(this.content);
    return m;
  }
}
