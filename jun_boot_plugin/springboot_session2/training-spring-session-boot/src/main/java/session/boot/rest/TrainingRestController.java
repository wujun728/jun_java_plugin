package session.boot.rest;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class TrainingRestController {

	@RequestMapping("/test")
	public String test(HttpSession session) {
		Random r = new Random();
		session.setAttribute("boot:" + r.nextLong(), UUID.randomUUID()
				.toString());
		return "rest:controller:hello:world";
	}

}
