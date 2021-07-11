package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.system.entity.RoleMenu;
import cc.mrbird.febs.system.mapper.RoleMenuMapper;
import cc.mrbird.febs.system.service.IRoleMenuService;
import cc.mrbird.febs.system.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private final IUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(List<String> roleIds) {
        baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(List<String> menuIds) {
        baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
    }

    @Override
    public Set<Long> findUserIdByMenuIds(List<String> menuIds) {
        List<RoleMenu> roleMenus = baseMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
        if (CollectionUtils.isNotEmpty(roleMenus)) {
            List<String> roleIds = roleMenus.stream().map(RoleMenu::getRoleId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            return userRoleService.findUserIdByRoleIds(roleIds);
        }
        return null;
    }

}
