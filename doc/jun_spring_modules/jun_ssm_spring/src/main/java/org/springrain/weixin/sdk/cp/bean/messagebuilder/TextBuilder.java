package org.springrain.weixin.sdk.cp.bean.messagebuilder;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 *
 * @author springrain
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
  private String content;

  public TextBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_TEXT;
  }

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}
