package org.springrain.weixin.sdk.cp.bean;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.util.xml.XStreamMediaIdConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxCpXmlOutVoiceMessage extends WxCpXmlOutMessage {
  private static final long serialVersionUID = -7947384031546099340L;

  @XStreamAlias("Voice")
  @XStreamConverter(value = XStreamMediaIdConverter.class)
  private String mediaId;

  public WxCpXmlOutVoiceMessage() {
    this.msgType = WxConsts.XML_MSG_VOICE;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
