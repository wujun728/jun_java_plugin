package session.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrainingController {

	public TrainingController() {
		System.out.println("===============");
	}

	@ResponseBody
	@RequestMapping("/test")
	public String test(HttpSession session) {
		Random r = new Random();
		session.setAttribute("temp:" + r.nextLong(), UUID.randomUUID()
				.toString());
		return "Hello World";
	}

}
