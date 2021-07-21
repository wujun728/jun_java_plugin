package cn.springmvc.mybatis.service.auth.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.common.exception.BusinessException;
import cn.springmvc.mybatis.common.utils.UUIDUtil;
import cn.springmvc.mybatis.common.utils.salt.Digests;
import cn.springmvc.mybatis.common.utils.salt.Encodes;
import cn.springmvc.mybatis.entity.auth.Role;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.entity.auth.UserRole;
import cn.springmvc.mybatis.mapper.auth.RoleMapper;
import cn.springmvc.mybatis.mapper.auth.UserMapper;
import cn.springmvc.mybatis.mapper.auth.UserRoleMapper;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.command.UserCommand;

/**
 * @author Vincent.wang
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    @Override
    public void addUser(User user, Role role) {
        if (user == null || role == null) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPassword())) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StringUtils.isBlank(role.getId())) {
            throw new BusinessException("user.registr.error", "用户未指定所属角色");
        }

        // Role r = daoService.getByPrimaryKey(Role.class, role.getId());
        Role r = roleMapper.findById(role.getId());
        if (r == null) {
            throw new BusinessException("user.registr.error", "用户未指定所属组织或角色");
        }

        entryptPassword(user);
        user.setStatus(Constants.STATUS_VALID);
        user.setCreateTime(Calendar.getInstance().getTime());
        // daoService.save(user);
        user.setId(UUIDUtil.getRandom32PK());
        userMapper.insert(user);

        UserRole ur = new UserRole();
        ur.setRoleId(r.getId());
        ur.setUserId(user.getId());
        ur.setId(UUIDUtil.getRandom32PK());
        // daoService.save(ur);
        userRoleMapper.insert(ur);
    }

    @Override
    public void updatePassword(UserCommand userCommand, User user) {
        if (log.isDebugEnabled()) {
            log.debug("## update User password.");
        }
        User u = userMapper.findById(user.getId());
        u.setPassword(userCommand.getPasswordAgain());
        entryptPassword(u);
        u.setModifyTime(Calendar.getInstance().getTime());
        // daoService.update(u);
        userMapper.update(u);
    }

    @Override
    public User findUserByName(String username) {
        try {
            return userMapper.findUserByName(username);
        } catch (Exception e) {
            log.error("# 根据账号查询用户报错 , username={}", username);
            throw new BusinessException("1001", "查询用户失败");
        }
    }

    @Override
    public void updateUserLastLoginTime(User user) {
        User u = userMapper.findById(user.getId());
        if (u != null) {
            user = new User();
            user.setLastLoginTime(Calendar.getInstance().getTime());
            user.setId(u.getId());
            userMapper.update(u);
        }
    }

    @Override
    public List<User> findUsers() {
        return userMapper.findUsers();
    }

    @Override
    public List<User> findEmp(String shopId, String empName) {
        return userMapper.findEmp(Constants.COMMON_ROLE_CODE, Constants.STATUS_VALID, shopId, empName);
    }
}
