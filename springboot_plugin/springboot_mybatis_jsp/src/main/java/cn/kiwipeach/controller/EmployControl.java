package cn.kiwipeach.controller;

import cn.kiwipeach.model.business.Employ;
import cn.kiwipeach.response.DataResponse;
import cn.kiwipeach.service.business.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Create Date: 2017/11/06
 * Description: 员工管理控制器
 *
 * @author Wujun
 */
@Controller
@RequestMapping("employ")
public class EmployControl {

    @Autowired
    private EmployService employService;

    @GetMapping("query")
    @ResponseBody
    public DataResponse<Employ> queryEmploy(
            @RequestParam(value = "empno",required = true) String empno
    ) {
        Employ employ = employService.queryEmploy(empno);
        //FIXME 此处有可能会抛出空指针
        String ename = employ.getEname();
        System.out.println("当前 emp name is==>"+ename);
        DataResponse<Employ> dataResponse = new DataResponse<Employ>(employ);
        return dataResponse;
    }

    @GetMapping("welcome")
    String toWelcomePage(){
        System.out.println("热部署:Hot Spring Boot777");
        return "welcome";
    }

    @GetMapping("welcome2")
    String toWelcomePage2(){
        System.out.println("热部署:Hot Spring Boot888");
        return "welcome";
    }


}
