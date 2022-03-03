package org.springrain.weixin.sdk.mp.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.weixin.sdk.common.api.IWxErrorExceptionHandler;
import org.springrain.weixin.sdk.common.util.LogExceptionHandler;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlMessage;
import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlOutMessage;

/**
 * <pre>
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 *
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link WxMpMessageRouterRule#next()}
 * 3. 规则的结束必须用{@link WxMpMessageRouterRule#end()}或者{@link WxMpMessageRouterRule#next()}，否则不会生效
 *
 * 使用方法：
 * WxMpMessageRouter router = new WxMpMessageRouter();
 * router
 *   .rule()
 *       .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 *       .interceptor(interceptor, ...).handler(handler, ...)
 *   .end()
 *   .rule()
 *       // 另外一个匹配规则
 *   .end()
 * ;
 *
 * // 将WxXmlMessage交给消息路由器
 * router.route(message);
 *
 * </pre>
 * @author springrain
 *
 */
public class WxMpMessageRouter {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final int DEFAULT_THREAD_POOL_SIZE = 100;

  private final List<WxMpMessageRouterRule> rules = new ArrayList<>();

  private final IWxMpService wxMpService;

  private ExecutorService executorService;

 // private IWxMessageDuplicateChecker messageDuplicateChecker;


  private IWxErrorExceptionHandler exceptionHandler;

  public WxMpMessageRouter(IWxMpService wxMpService) {
    this.wxMpService = wxMpService;
   // this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    
    
    this.executorService = new ThreadPoolExecutor(10, DEFAULT_THREAD_POOL_SIZE, 10L,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
    
   // this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
    this.exceptionHandler = new LogExceptionHandler();
  }

  /**
   * <pre>
   * 设置自定义的 {@link ExecutorService}
   * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
   * </pre>
   * @param executorService
   */
  public void setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * <pre>
   * 设置自定义的 {@link org.springrain.weixin.sdk.common.api.IWxMessageDuplicateChecker}
   * 如果不调用该方法，默认使用 {@link org.springrain.weixin.sdk.common.api.WxMessageInMemoryDuplicateChecker}
   * </pre>
   * @param messageDuplicateChecker
   */
  /*
  public void setMessageDuplicateChecker(IWxMessageDuplicateChecker messageDuplicateChecker) {
    this.messageDuplicateChecker = messageDuplicateChecker;
  }
  */
  

  /**
   * <pre>
   * 设置自定义的{@link org.springrain.weixin.sdk.common.api.IWxErrorExceptionHandler}
   * 如果不调用该方法，默认使用 {@link org.springrain.weixin.sdk.common.util.LogExceptionHandler}
   * </pre>
   * @param exceptionHandler
   */
  public void setExceptionHandler(IWxErrorExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  List<WxMpMessageRouterRule> getRules() {
    return this.rules;
  }

  /**
   * 开始一个新的Route规则
   */
  public WxMpMessageRouterRule rule() {
    return new WxMpMessageRouterRule(this);
  }

  /**
   * 处理微信消息
   * @param wxMessage
   */
  public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage) {
   // if (isDuplicateMessage(wxMessage)) {
      // 如果是重复消息，那么就不做处理
   //   return null;
   // }

    final List<WxMpMessageRouterRule> matchRules = new ArrayList<>();
    // 收集匹配的规则
    for (final WxMpMessageRouterRule rule : this.rules) {
      if (rule.test(wxMessage)) {
        matchRules.add(rule);
        if(!rule.isReEnter()) {
          break;
        }
      }
    }

    if (matchRules.size() == 0) {
      return null;
    }

    WxMpXmlOutMessage res = null;
    final List<Future<?>> futures = new ArrayList<>();
    for (final WxMpMessageRouterRule rule : matchRules) {
      // 返回最后一个非异步的rule的执行结果
      if(rule.isAsync()) {
        futures.add(
            this.executorService.submit(new Runnable() {
              @Override
              public void run() {
                rule.service(wxMessage, WxMpMessageRouter.this.wxMpService, WxMpMessageRouter.this.exceptionHandler);
              }
            })
        );
      } else {
        res = rule.service(wxMessage, this.wxMpService, this.exceptionHandler);
      }
    }

    if (futures.size() > 0) {
      this.executorService.submit(new Runnable() {
        @Override
        public void run() {
          for (Future<?> future : futures) {
            try {
              future.get();
              WxMpMessageRouter.this.log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUser());
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              WxMpMessageRouter.this.log.error("Error happened when wait task finish", e);
            } catch (ExecutionException e) {
              WxMpMessageRouter.this.log.error("Error happened when wait task finish", e);
            }
          }
        }
      });
    }
    return res;
  }

  /*
  protected boolean isDuplicateMessage(WxMpXmlMessage wxMessage) {

    StringBuilder messageId = new StringBuilder();
    if (wxMessage.getMsgId() == null) {
      messageId.append(wxMessage.getCreateTime())
        .append("-").append(wxMessage.getFromUser())
        .append("-").append(wxMessage.getEventKey() == null ? "" : wxMessage.getEventKey())
        .append("-").append(wxMessage.getEvent() == null ? "" : wxMessage.getEvent())
      ;
    } else {
      messageId.append(wxMessage.getMsgId());
    }

    return this.messageDuplicateChecker.isDuplicate(messageId.toString());

  }
  */

}
