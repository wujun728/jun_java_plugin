package org.springrain.weixin.sdk.mp.builder.outxml;

import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlOutVideoMessage;

/**
 * 视频消息builder
 * @author springrain
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxMpXmlOutVideoMessage> {

  private String mediaId;
  private String title;
  private String description;

  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }
  public VideoBuilder description(String description) {
    this.description = description;
    return this;
  }
  public VideoBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public WxMpXmlOutVideoMessage build() {
    WxMpXmlOutVideoMessage m = new WxMpXmlOutVideoMessage();
    setCommon(m);
    m.setTitle(this.title);
    m.setDescription(this.description);
    m.setMediaId(this.mediaId);
    return m;
  }

}
