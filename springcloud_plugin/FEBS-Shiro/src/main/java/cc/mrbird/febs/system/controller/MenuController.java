package cc.mrbird.febs.system.controller;


import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Menu;
import cc.mrbird.febs.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @GetMapping("{username}")
    public FebsResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws FebsException {
        if (!StringUtils.equalsIgnoreCase(username, getCurrentUser().getUsername())) {
            throw new FebsException("您无权获取别人的菜单");
        }
        return new FebsResponse().data(menuService.findUserMenus(username));
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public FebsResponse getMenuTree(Menu menu) {
        return new FebsResponse().success()
                .data(menuService.findMenus(menu).getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public FebsResponse addMenu(@Valid Menu menu) {
        menuService.createMenu(menu);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public FebsResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        menuService.deleteMenus(menuIds);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public FebsResponse updateMenu(@Valid Menu menu) {
        menuService.updateMenu(menu);
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        ExcelKit.$Export(Menu.class, response).downXlsx(menuService.findMenuList(menu), false);
    }
}
