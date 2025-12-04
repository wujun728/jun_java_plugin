package com.flying.cattle.wf.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.MongoService;

import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/view")
public class ViewController {
	@Autowired
	private MongoService mongoService;
	
	@GetMapping("/user/page")
    public String listPage(final Model model) {
        final Flux<User> users = mongoService.findAllUser();
        model.addAttribute("users", users);
        return "user";
    }
	
	@GetMapping("/commodity/list")
    public String CommoditylistPage(final Model model) {
        return "commodity";
    }

}
