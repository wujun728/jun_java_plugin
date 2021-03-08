package progressbar;

/**
 * Created by qianjia on 2017/3/17.
 */
public class SimpleProgressBar implements ProgressBar {

  private String id;

  private String title;

  private int total;

  private int progress;

  private boolean finished;

  public SimpleProgressBar(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void start(String title) {
    this.title = title;
  }

  @Override
  public void start(String title, int total) {
    this.title = title;
    if (total < 0) {
      return;
    }
    this.total = total;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public void setTotal(int total) {
    this.total = total;
  }

  @Override
  public void forward() {
    if (this.progress >= this.total) {
      return;
    }
    this.progress++;
  }

  @Override
  public void forward(int step) {
    if (step < 0) {
      return;
    }
    if (this.progress + step > this.total) {
      this.progress = this.total;
      return;
    }
    this.progress += step;
  }

  @Override
  public void rewind() {
    if (this.progress == 0) {
      return;
    }
    this.progress--;
  }

  @Override
  public void rewind(int step) {
    if (step < 0) {
      return;
    }
    if (this.progress - step < 0) {
      this.progress = 0;
      return;
    }
    this.progress -= step;
  }

  @Override
  public void finish() {
    this.finished = true;
  }

  @Override
  public void setProgress(int progress) {
    if (progress < 0) {
      this.progress = 0;
      return;
    }
    this.progress = this.total < progress ? this.total : progress;
  }

  @Override
  public boolean isFinished() {
    return this.finished;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public int getTotal() {
    return total;
  }

  @Override
  public int getProgress() {
    return progress;
  }

}
