package org.springrain.weixin.sdk.mp.builder.outxml;

import org.apache.commons.lang3.StringUtils;
import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlOutTransferKefuMessage;

/**
 * 客服消息builder
 * <pre>
 * 用法: WxMpKefuMessage m = WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().content(...).toUser(...).build();
 * </pre>
 *
 * @author springrain
 */
public final class TransferCustomerServiceBuilder extends BaseBuilder<TransferCustomerServiceBuilder, WxMpXmlOutTransferKefuMessage> {
  private String kfAccount;

  public TransferCustomerServiceBuilder kfAccount(String kf) {
    this.kfAccount = kf;
    return this;
  }

  @Override
  public WxMpXmlOutTransferKefuMessage build() {
    WxMpXmlOutTransferKefuMessage m = new WxMpXmlOutTransferKefuMessage();
    setCommon(m);
    if(StringUtils.isNotBlank(this.kfAccount)){
      WxMpXmlOutTransferKefuMessage.TransInfo transInfo = new WxMpXmlOutTransferKefuMessage.TransInfo();
      transInfo.setKfAccount(this.kfAccount);
      m.setTransInfo(transInfo);
    }
    return m;
  }
}
