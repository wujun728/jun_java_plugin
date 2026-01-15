package io.github.wujun728.minimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最简单的控制器，仅用于测试应用是否能正常启动
 */
@RestController
public class MinimalController {

    @GetMapping("/minimal/hello")
    public String hello() {
        return "Hello from minimal controller!";
    }
}