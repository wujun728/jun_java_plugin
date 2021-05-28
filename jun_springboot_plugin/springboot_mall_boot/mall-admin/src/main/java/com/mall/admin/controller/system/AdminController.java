package com.mall.admin.controller.system;

import com.mall.admin.service.AdminService;
import com.mall.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/12/22 21:58
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<Map> list(@RequestParam Map params) {
        return adminService.list(params);
    }


}
