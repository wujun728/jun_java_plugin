package org.springrain.weixin.sdk.mp.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springrain.weixin.sdk.common.api.IWxErrorExceptionHandler;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpMessageHandler;
import org.springrain.weixin.sdk.mp.api.IWxMpMessageInterceptor;
import org.springrain.weixin.sdk.mp.api.IWxMpMessageMatcher;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlMessage;
import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlOutMessage;

public class WxMpMessageRouterRule {

  private final WxMpMessageRouter routerBuilder;

  private boolean async = true;

  private String fromUser;

  private String msgType;

  private String event;

  private String eventKey;

  private String content;

  private String rContent;

  private IWxMpMessageMatcher matcher;

  private boolean reEnter = false;

  private List<IWxMpMessageHandler> handlers = new ArrayList<>();

  private List<IWxMpMessageInterceptor> interceptors = new ArrayList<>();

  public WxMpMessageRouterRule(WxMpMessageRouter routerBuilder) {
    this.routerBuilder = routerBuilder;
  }

  /**
   * 设置是否异步执行，默认是true
   */
  public WxMpMessageRouterRule async(boolean async) {
    this.async = async;
    return this;
  }

  /**
   * 如果msgType等于某值
   */
  public WxMpMessageRouterRule msgType(String msgType) {
    this.msgType = msgType;
    return this;
  }

  /**
   * 如果event等于某值
   */
  public WxMpMessageRouterRule event(String event) {
    this.event = event;
    return this;
  }

  /**
   * 如果eventKey等于某值
   */
  public WxMpMessageRouterRule eventKey(String eventKey) {
    this.eventKey = eventKey;
    return this;
  }

  /**
   * 如果content等于某值
   */
  public WxMpMessageRouterRule content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 如果content匹配该正则表达式
   */
  public WxMpMessageRouterRule rContent(String regex) {
    this.rContent = regex;
    return this;
  }

  /**
   * 如果fromUser等于某值
   */
  public WxMpMessageRouterRule fromUser(String fromUser) {
    this.fromUser = fromUser;
    return this;
  }

  /**
   * 如果消息匹配某个matcher，用在用户需要自定义更复杂的匹配规则的时候
   */
  public WxMpMessageRouterRule matcher(IWxMpMessageMatcher matcher) {
    this.matcher = matcher;
    return this;
  }

  /**
   * 设置微信消息拦截器
   */
  public WxMpMessageRouterRule interceptor(IWxMpMessageInterceptor interceptor) {
    return interceptor(interceptor, (IWxMpMessageInterceptor[]) null);
  }

  /**
   * 设置微信消息拦截器
   */
  public WxMpMessageRouterRule interceptor(IWxMpMessageInterceptor interceptor, IWxMpMessageInterceptor... otherInterceptors) {
    this.interceptors.add(interceptor);
    if (otherInterceptors != null && otherInterceptors.length > 0) {
      for (IWxMpMessageInterceptor i : otherInterceptors) {
        this.interceptors.add(i);
      }
    }
    return this;
  }

  /**
   * 设置微信消息处理器
   */
  public WxMpMessageRouterRule handler(IWxMpMessageHandler handler) {
    return handler(handler, (IWxMpMessageHandler[]) null);
  }

  /**
   * 设置微信消息处理器
   */
  public WxMpMessageRouterRule handler(IWxMpMessageHandler handler, IWxMpMessageHandler... otherHandlers) {
    this.handlers.add(handler);
    if (otherHandlers != null && otherHandlers.length > 0) {
      for (IWxMpMessageHandler i : otherHandlers) {
        this.handlers.add(i);
      }
    }
    return this;
  }

  /**
   * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
   */
  public WxMpMessageRouter end() {
    this.routerBuilder.getRules().add(this);
    return this.routerBuilder;
  }

  /**
   * 规则结束，但是消息还会进入其他规则
   */
  public WxMpMessageRouter next() {
    this.reEnter = true;
    return end();
  }

  /**
   * 将微信自定义的事件修正为不区分大小写,
   * 比如框架定义的事件常量为click，但微信传递过来的却是CLICK
   */
  protected boolean test(WxMpXmlMessage wxMessage) {
    return
        (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUser()))
            &&
            (this.msgType == null || this.msgType.toLowerCase().equals((wxMessage.getMsgType()==null?null:wxMessage.getMsgType().toLowerCase())))
            &&
            (this.event == null || this.event.toLowerCase().equals((wxMessage.getEvent()==null?null:wxMessage.getEvent().toLowerCase())))
            &&
            (this.eventKey == null || this.eventKey.toLowerCase().equals((wxMessage.getEventKey()==null?null:wxMessage.getEventKey().toLowerCase())))
            &&
            (this.content == null || this.content
                .equals(wxMessage.getContent() == null ? null : wxMessage.getContent().trim()))
            &&
            (this.rContent == null || Pattern
                .matches(this.rContent, wxMessage.getContent() == null ? "" : wxMessage.getContent().trim()))
            &&
            (this.matcher == null || this.matcher.match(wxMessage))
        ;
  }

  /**
   * 处理微信推送过来的消息
   *
   * @param wxMessage
   * @return true 代表继续执行别的router，false 代表停止执行别的router
   */
  protected WxMpXmlOutMessage service(WxMpXmlMessage wxMessage,
      IWxMpService wxMpService,
      IWxErrorExceptionHandler exceptionHandler) {

    try {

      Map<String, Object> context = new HashMap<>();
      // 如果拦截器不通过
      for (IWxMpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxMpService)) {
          return null;
        }
      }

      // 交给handler处理
      WxMpXmlOutMessage res = null;
      for (IWxMpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        if(handler == null){
          continue;
        }
        res = handler.handle(wxMessage, context, wxMpService);
      }
      return res;
    } catch (WxErrorException e) {
      exceptionHandler.handle(e);
    }
    return null;

  }

  public WxMpMessageRouter getRouterBuilder() {
    return this.routerBuilder;
  }

  public boolean isAsync() {
    return this.async;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  public String getFromUser() {
    return this.fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getMsgType() {
    return this.msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getEvent() {
    return this.event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getEventKey() {
    return this.eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getrContent() {
    return this.rContent;
  }

  public void setrContent(String rContent) {
    this.rContent = rContent;
  }

  public IWxMpMessageMatcher getMatcher() {
    return this.matcher;
  }

  public void setMatcher(IWxMpMessageMatcher matcher) {
    this.matcher = matcher;
  }

  public boolean isReEnter() {
    return this.reEnter;
  }

  public void setReEnter(boolean reEnter) {
    this.reEnter = reEnter;
  }

  public List<IWxMpMessageHandler> getHandlers() {
    return this.handlers;
  }

  public void setHandlers(List<IWxMpMessageHandler> handlers) {
    this.handlers = handlers;
  }

  public List<IWxMpMessageInterceptor> getInterceptors() {
    return this.interceptors;
  }

  public void setInterceptors(List<IWxMpMessageInterceptor> interceptors) {
    this.interceptors = interceptors;
  }
}
