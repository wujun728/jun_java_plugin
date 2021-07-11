package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.MenuTree;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.event.UserAuthenticationUpdatedEventPublisher;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.entity.Menu;
import cc.mrbird.febs.system.mapper.MenuMapper;
import cc.mrbird.febs.system.service.IMenuService;
import cc.mrbird.febs.system.service.IRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final IRoleMenuService roleMenuService;
    private final UserAuthenticationUpdatedEventPublisher publisher;

    @Override
    public List<Menu> findUserPermissions(String username) {
        return baseMapper.findUserPermissions(username);
    }

    @Override
    public MenuTree<Menu> findUserMenus(String username) {
        List<Menu> menus = baseMapper.findUserMenus(username);
        List<MenuTree<Menu>> trees = convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }

    @Override
    public MenuTree<Menu> findMenus(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        List<MenuTree<Menu>> trees = convertMenus(menus);

        return TreeUtil.buildMenuTree(trees);
    }

    @Override
    public List<Menu> findMenuList(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getMenuId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(Menu menu) {
        menu.setCreateTime(new Date());
        setMenu(menu);
        baseMapper.insert(menu);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        menu.setModifyTime(new Date());
        setMenu(menu);
        baseMapper.updateById(menu);

        Set<Long> userIds = roleMenuService.findUserIdByMenuIds(Lists.newArrayList(String.valueOf(menu.getMenuId())));
        if (CollectionUtils.isNotEmpty(userIds)) {
            publisher.publishEvent(userIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenus(String menuIds) {
        List<String> menuIdList = Arrays.asList(menuIds.split(Strings.COMMA));
        delete(menuIdList);
        Set<Long> userIds = roleMenuService.findUserIdByMenuIds(menuIdList);
        if (CollectionUtils.isNotEmpty(userIds)) {
            publisher.publishEvent(userIds);
        }
    }

    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(String.valueOf(menu.getMenuId()));
            tree.setParentId(String.valueOf(menu.getParentId()));
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getUrl());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_NODE);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
    }

    private void delete(List<String> menuIds) {
        List<String> list = new ArrayList<>(menuIds);
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            list.addAll(menuIdList);
            roleMenuService.deleteRoleMenusByMenuId(list);
            delete(menuIdList);
        } else {
            roleMenuService.deleteRoleMenusByMenuId(list);
        }
    }
}
