package isolation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-isolation.properties")
public interface OracleTxIsolationTestApplication {
}
