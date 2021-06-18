package com.mall.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * @author Wujun
 * @version 1.0
 * @create_at 2019/1/19 19:15
 */
@Service
public class MenuService extends AdminBaseService {

    @Autowired
    private RoleService roleService;

    /**
     * 菜单列表
     * @param params
     * @return
     */
    public List<Map> getMenu(@RequestParam Map params) {
        return sqlSessionTemplate.selectList("system.menu.list", params);
    }

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    public List<Map> getMenuByUser(int userId) {
        List<Map> roleList = roleService.getRoleByUser(userId);
        List<Integer> roleIds = new ArrayList<>();
        roleList.stream().forEach(item -> roleIds.add((Integer)item.get("id")));
        return getMenuByRole(roleIds);
    }

    public List<Map> getMenuByRole(List<Integer> roleIds) {
        return sqlSessionTemplate.selectList("system.menu.getMenuByRole", roleIds);
    }

    public List<Map> getMenuByParent() {
        return findParent(new ArrayList<Map>(), 0);
    }

    /**
     * 获取顶级分类菜单
     * @author Wujun
     * @version 1.0
     * @create 2018/11/8 10:24
     **/
    public List<Map> findParent(List<Map> result, int parentId) {
        List<Map> resultList = getChildren(parentId);
        for (Map menuMap : resultList) {
            List<Map> childrenList = getChildren((Integer) menuMap.get("value"));
            if (childrenList != null && childrenList.size() > 0) {
                menuMap.put("children", childrenList);
            }
            result.add(menuMap);
        }
        return result;
    }

    /**
     * 获取子类菜单
     * @author Wujun
     * @version 1.0
     * @create 2018/11/7 17:17
     **/
    private List<Map> getChildren(int parentId) {
        List<Map> childrenList =  sqlSessionTemplate.selectList("system.menu.list.findByParentId", parentId);
        for (Map children : childrenList) {
            List<Map> list = getChildren((Integer) children.get("value"));
            if (list != null && list.size() > 0) {
                children.put("children", list);
            }
        }
        return childrenList;
    }
}
