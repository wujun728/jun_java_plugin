package org.springrain.weixin.sdk.cp.bean.messagebuilder;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;

public class BaseBuilder<T> {
  protected String msgType;
  protected Integer agentId;
  protected String toUser;
  protected String toParty;
  protected String toTag;
  protected String safe;

  @SuppressWarnings("unchecked")
public T agentId(Integer agentId) {
    this.agentId = agentId;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
public T toParty(String toParty) {
    this.toParty = toParty;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
public T toTag(String toTag) {
    this.toTag = toTag;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
public T safe(String safe) {
    this.safe = safe;
    return (T) this;
  }

  public WxCpMessage build() {
    WxCpMessage m = new WxCpMessage();
    m.setAgentId(this.agentId);
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    m.setToParty(this.toParty);
    m.setToTag(this.toTag);
    m.setSafe(
            (this.safe == null || "".equals(this.safe)) ? WxConsts.CUSTOM_MSG_SAFE_NO : this.safe);
    return m;
  }

}
