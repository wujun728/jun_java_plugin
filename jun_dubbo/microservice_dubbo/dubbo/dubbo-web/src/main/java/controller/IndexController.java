package controller;

import com.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/12 22:00
 */
@RestController
public class IndexController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/")
    public String index() {
        demoService.sayHello("test web");
        return "success";
    }
}
