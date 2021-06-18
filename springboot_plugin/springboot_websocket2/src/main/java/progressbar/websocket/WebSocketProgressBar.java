package progressbar.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import progressbar.SimpleProgressBar;

/**
 * Created by qianjia on 2017/3/17.
 */
public class WebSocketProgressBar extends SimpleProgressBar {

  private SimpMessagingTemplate messagingTemplate;

  public WebSocketProgressBar(String id, SimpMessagingTemplate messagingTemplate) {
    super(id);
    this.messagingTemplate = messagingTemplate;
  }

  @Override
  public void start(String title) {
    super.start(title);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void start(String title, int total) {
    super.start(title, total);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void setTotal(int total) {
    super.setTotal(total);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void forward() {
    super.forward();
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void forward(int step) {
    super.forward(step);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void rewind() {
    super.rewind();
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void rewind(int step) {
    super.rewind(step);
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void finish() {
    super.finish();
    sendMessage(new ProgressBarStatus(this));
  }

  @Override
  public void setProgress(int progress) {
    super.setProgress(progress);
    sendMessage(new ProgressBarStatus(this));
  }

  private String getDestination() {
    return "/queue/progressbar/" + getId();
  }

  private void sendMessage(ProgressBarStatus command) {
    messagingTemplate.convertAndSend(getDestination(), command);
  }

}

