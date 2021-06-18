package cn.springboot.service.auth.impl;

import cn.springboot.common.constants.Constants;
import cn.springboot.common.exception.BusinessException;
import cn.springboot.common.util.salt.Digests;
import cn.springboot.common.util.salt.Encodes;
import cn.springboot.config.table.FactoryAboutKey;
import cn.springboot.config.table.TablesPKEnum;
import cn.springboot.mapper.auth.RoleMapper;
import cn.springboot.mapper.auth.UserMapper;
import cn.springboot.mapper.auth.UserRoleMapper;
import cn.springboot.model.auth.Role;
import cn.springboot.model.auth.User;
import cn.springboot.model.auth.UserRole;
import cn.springboot.service.auth.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service("userService")
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

    @Transactional
    @Override
    public void addUser(User user, Role role) {
        if (user == null || role == null) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
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
        
        User u = userMapper.findUserByName(user.getUsername());
        if(u!=null){
            throw new BusinessException("user.registr.error", "用户账号已经存在,username="+user.getUsername());
        }

        entryptPassword(user);
        user.setStatus(Constants.STATUS_VALID);
        user.setCreateTime(Calendar.getInstance().getTime());
        user.setId(FactoryAboutKey.getPK(TablesPKEnum.T_SYS_USER));
        userMapper.insert(user);

        UserRole ur = new UserRole();
        ur.setRoleId(r.getId());
        ur.setUserId(user.getId());
        ur.setId(FactoryAboutKey.getPK(TablesPKEnum.T_SYS_USER_ROLE));
        // daoService.save(ur);
        userRoleMapper.insert(ur);
    }

    @Override
    public void updatePassword(User user) {
        if (log.isDebugEnabled()) {
            log.debug("## update User password.");
        }
        User u = userMapper.findById(user.getId());
        u.setPassword(user.getPassword());
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

    @Transactional
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
