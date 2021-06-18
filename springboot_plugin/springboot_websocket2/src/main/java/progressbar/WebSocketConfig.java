package progressbar;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // 启用简单Message Broker，客户端（浏览器）先通过WebSocket接入点和应用取得连接
    // 然后再发送或者获得消息，消息的目标以 /topic/ 前缀都会被接入到简单Message Broker
    config.enableSimpleBroker("/queue", "/topic");
    // 客户端（浏览器）发送消息到 message-handling 比如 @MessageMapping所映射的url的前缀
    // 比如@MessageMapping("/hello")，那么客户端必须发送 /app/hello 才行
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // 客户端（浏览器）利用 SockJS 或者 WebSocket 和服务器通信时，所使用的接入点
    // 只有通过这些接入点发送过来的消息，才会被后端的WebSocket处理器所处理
    registry.addEndpoint("/_stomp").withSockJS();
  }

}
