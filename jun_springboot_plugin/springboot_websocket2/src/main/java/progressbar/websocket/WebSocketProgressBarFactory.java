package progressbar.websocket;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import progressbar.ProgressBar;
import progressbar.ProgressBarFactory;

/**
 * Created by qianjia on 2017/3/17.
 */
@Component
public class WebSocketProgressBarFactory implements ProgressBarFactory {

  private SimpMessagingTemplate messagingTemplate;

  @Override
  public ProgressBar create() {
    String id = RandomStringUtils.randomAlphabetic(10);
    WebSocketProgressBar webSocketProgressBar = new WebSocketProgressBar(id, messagingTemplate);
    return webSocketProgressBar;
  }

  @Autowired
  @Qualifier("brokerMessagingTemplate")
  public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

}
