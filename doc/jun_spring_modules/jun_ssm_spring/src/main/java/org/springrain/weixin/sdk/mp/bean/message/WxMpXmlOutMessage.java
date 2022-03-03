package org.springrain.weixin.sdk.mp.bean.message;

import java.io.Serializable;

import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.util.xml.XStreamCDataConverter;
import org.springrain.weixin.sdk.mp.builder.outxml.ImageBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.MusicBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.NewsBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.TextBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.TransferCustomerServiceBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.VideoBuilder;
import org.springrain.weixin.sdk.mp.builder.outxml.VoiceBuilder;
import org.springrain.weixin.sdk.mp.util.crypto.WxMpCryptUtil;
import org.springrain.weixin.sdk.mp.util.xml.XStreamTransformer;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public abstract class WxMpXmlOutMessage implements Serializable {

  private static final long serialVersionUID = -381382011286216263L;

  @XStreamAlias("ToUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String toUserName;

  @XStreamAlias("FromUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String fromUserName;

  @XStreamAlias("CreateTime")
  protected Long createTime;

  @XStreamAlias("MsgType")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String msgType;

  public String getToUserName() {
    return this.toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public String getFromUserName() {
    return this.fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public Long getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public String getMsgType() {
    return this.msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  @SuppressWarnings("unchecked")
public String toXml() {
    return XStreamTransformer.toXml((Class<WxMpXmlOutMessage>) this.getClass(), this);
  }

  /**
   * 转换成加密的xml格式
   */
  public String toEncryptedXml(IWxConfig wxconfig) {
    String plainXml = toXml();
    WxMpCryptUtil pc = new WxMpCryptUtil(wxconfig);
    return pc.encrypt(plainXml);
  }

  /**
   * 获得文本消息builder
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得图片消息builder
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }

  /**
   * 获得视频消息builder
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }

  /**
   * 获得音乐消息builder
   */
  public static MusicBuilder MUSIC() {
    return new MusicBuilder();
  }

  /**
   * 获得图文消息builder
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }

  /**
   * 获得客服消息builder
   */
  public static TransferCustomerServiceBuilder TRANSFER_CUSTOMER_SERVICE() {
    return new TransferCustomerServiceBuilder();
  }
}
