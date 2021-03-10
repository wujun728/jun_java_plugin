package com.mall.admin.controller.system;

import com.mall.admin.service.MenuService;
import com.mall.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2414:54
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Map> list(@RequestParam Map params) {
        return menuService.getMenu(params);
    }

    @GetMapping("getMenuByUser")
    public List<Map> getMenuByParent() {
        return menuService.getMenuByParent();
       // return menuService.getMenuByUser(userId);
    }

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    @GetMapping("getMenuByUser")
    public List<Map> getMenuByUser(int userId) {
        return menuService.getMenuByUser(userId);
    }
}
