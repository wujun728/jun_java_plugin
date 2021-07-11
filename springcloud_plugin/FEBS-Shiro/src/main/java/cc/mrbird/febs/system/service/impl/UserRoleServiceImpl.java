package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.system.entity.UserRole;
import cc.mrbird.febs.system.mapper.UserRoleMapper;
import cc.mrbird.febs.system.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByRoleId(List<String> roleIds) {
        baseMapper.delete(new QueryWrapper<UserRole>().lambda().in(UserRole::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByUserId(List<String> userIds) {
        baseMapper.delete(new QueryWrapper<UserRole>().lambda().in(UserRole::getUserId, userIds));
    }

    @Override
    public Set<Long> findUserIdByRoleId(Long roleId) {
        List<UserRole> userRoles = baseMapper.selectList(new QueryWrapper<UserRole>().lambda()
                .eq(UserRole::getRoleId, roleId));
        if (CollectionUtils.isNotEmpty(userRoles)) {
            return userRoles.stream().map(UserRole::getUserId).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public Set<Long> findUserIdByRoleIds(List<String> roleIds) {
        List<UserRole> userRoles = baseMapper.selectList(new QueryWrapper<UserRole>().lambda()
                .in(UserRole::getRoleId, roleIds));
        if (CollectionUtils.isNotEmpty(userRoles)) {
            return userRoles.stream().map(UserRole::getUserId).collect(Collectors.toSet());
        }
        return null;
    }
}
