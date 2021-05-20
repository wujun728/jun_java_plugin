package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.ReactiveRedisOperations;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  @Autowired
  private ReactiveRedisOperations<String, String> redisOperations;

  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    Application bean = context.getBean(Application.class);
    bean.run();

  }

  private void run() throws InterruptedException {
    while (true) {
      TimeUnit.SECONDS.sleep(3);
      redisOperations.opsForValue()
        .increment("foo")
        .subscribe(num -> LOGGER.info("INCR foo: " + num));
    }
  }

}
