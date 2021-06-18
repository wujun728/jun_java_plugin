package progressbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考spring官方例子：<br>
 * https://spring.io/guides/gs/messaging-stomp-websocket/ <br>
 * https://github.com/spring-guides/gs-messaging-stomp-websocket
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
