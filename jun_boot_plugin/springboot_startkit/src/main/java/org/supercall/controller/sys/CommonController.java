package org.supercall.controller.sys;

import org.springframework.web.bind.annotation.*;
import org.supercall.dao.SysMenuMapper;
import org.supercall.domainModel.MenuModel;
import org.supercall.models.SysMenu;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kira on 16/8/2.
 */
@RequestMapping("/admin/common")
@RestController
public class CommonController {
    @Resource
    SysMenuMapper sysMenuMapper;

    @RequestMapping(value = "/loadMenu", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    List<MenuModel> index(
            HttpServletRequest request) throws ClassNotFoundException {
        List<SysMenu> menus = sysMenuMapper.selectByExample(null);
        List<MenuModel> menuModelList = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentid() == null) {
                List<MenuModel> childrens = new ArrayList<MenuModel>();
                MenuModel entity = new MenuModel(menu.getName(), menu.getUrls()+"/"+menu.getName(), childrens, "", menu.getIcons());
                for (SysMenu sysMenu : menus) {
                    if (menu.getId().equals(sysMenu.getParentid())) {
                        MenuModel child = new MenuModel(sysMenu.getName(), sysMenu.getUrls()+"/"+sysMenu.getName(), null, "", sysMenu.getIcons());
                        childrens.add(child);
                    }
                }
                menuModelList.add(entity);
            }
        }
        return menuModelList;
    }

}
