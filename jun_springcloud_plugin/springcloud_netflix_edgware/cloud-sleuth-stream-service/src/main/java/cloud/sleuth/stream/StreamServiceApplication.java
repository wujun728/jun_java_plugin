package cloud.sleuth.stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class StreamServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StreamServiceApplication.class, args);
	}
}

@RestController
class HiController {

	private static final Log log = LogFactory.getLog(HiController.class);

	@RequestMapping("/hello")
	public String hi() throws Exception {
		log.info("helloservice");
		Thread.sleep(100L);
		return "helloservice";
	}
}