package com.springboot.springbootthymeleaf.controller;

import com.springboot.springbootthymeleaf.model.CourseModel;
import com.springboot.springbootthymeleaf.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/9/16 15:22
 * @Version: 1.0
 * @Desc:
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        // 构建测试数据
        Map<String, Object> map = new HashMap<>();

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Spring Boot");
        userModel.setAge(18);

        map.put("user", userModel);

        List<CourseModel> list = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            CourseModel courseMode = new CourseModel();
            courseMode.setId((long) i);
            courseMode.setName("Spring Boot：" + i);
            courseMode.setAddress("课程地点：" + i);
            list.add(courseMode);
        }

        map.put("list", list);

        map.put("flag", true);

        request.setAttribute("data", map);
        return "hello";
    }
}
