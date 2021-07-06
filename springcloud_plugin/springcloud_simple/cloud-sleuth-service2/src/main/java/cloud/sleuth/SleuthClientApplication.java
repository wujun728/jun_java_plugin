package cloud.sleuth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SleuthClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SleuthClientApplication.class, args);
	}
}

@RestController
class HiController {

	private static final Log log = LogFactory.getLog(HiController.class);

	@RequestMapping("/service2")
	public String hi() throws Exception {
		log.info("service2");
		Thread.sleep(100L);
		return "service2";
	}
}