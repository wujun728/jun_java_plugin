package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("home")
public class HomeController {

    @GetMapping("/console")
    public String console(@RequestParam(name="name", required=false, defaultValue="测试") String name, Model model) {
        model.addAttribute("name", name);
        return "home/console";
    }

}
