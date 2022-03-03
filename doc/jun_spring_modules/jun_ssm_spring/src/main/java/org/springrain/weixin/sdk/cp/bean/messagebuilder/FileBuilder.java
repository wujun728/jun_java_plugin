package org.springrain.weixin.sdk.cp.bean.messagebuilder;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.FILE().mediaId(...).toUser(...).build();
 * </pre>
 *
 * @author springrain
 */
public final class FileBuilder extends BaseBuilder<FileBuilder> {
  private String mediaId;

  public FileBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_FILE;
  }

  public FileBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  @Override
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
