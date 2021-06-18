package self.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index控制器
 */
@Controller
public class IndexController {
    /**
     * 默认路径
     */
    @GetMapping("/")
    public String index(Model model){

        return "index";
    }
}
