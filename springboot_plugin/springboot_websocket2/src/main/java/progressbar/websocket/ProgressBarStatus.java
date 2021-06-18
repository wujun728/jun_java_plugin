package progressbar.websocket;

import progressbar.ProgressBar;

import java.io.Serializable;

/**
 * Created by qianjia on 2017/3/17.
 */
public class ProgressBarStatus implements Serializable {

  private final String title;
  private final int progress;
  private final int total;
  private final boolean finished;

  public ProgressBarStatus(ProgressBar progressBar) {
    title = progressBar.getTitle();
    progress = progressBar.getProgress();
    total = progressBar.getTotal();
    finished = progressBar.isFinished();
  }

  public String getTitle() {
    return title;
  }

  public int getProgress() {
    return progress;
  }

  public int getTotal() {
    return total;
  }

  public boolean isFinished() {
    return this.finished;
  }

}
