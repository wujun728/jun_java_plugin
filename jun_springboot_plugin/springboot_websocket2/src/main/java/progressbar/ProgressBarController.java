package progressbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import progressbar.websocket.WebSocketProgressBarFactory;

/**
 * Created by qianjia on 2017/3/17.
 */
@RestController
public class ProgressBarController {

  @Autowired
  private WebSocketProgressBarFactory progressBarFactory;

  @RequestMapping("/progressbar/start")
  public String newProgressBar(@RequestParam("title") String title, @RequestParam("total") int total) {

    ProgressBar progressBar = progressBarFactory.create();
    new Thread(() -> {

      progressBar.start(title, total);

      try {
        while (progressBar.getProgress() < progressBar.getTotal()) {
          Thread.sleep(1000l);
          progressBar.forward();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      progressBar.finish();

    }).start();

    return progressBar.getId();

  }

}
