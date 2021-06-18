package cn.springmvc.jpa.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.common.exception.BusinessException;
import cn.springmvc.jpa.common.utils.salt.Digests;
import cn.springmvc.jpa.common.utils.salt.Encodes;
import cn.springmvc.jpa.entity.Role;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.entity.UserRole;
import cn.springmvc.jpa.repository.RoleRepository;
import cn.springmvc.jpa.repository.UserRepository;
import cn.springmvc.jpa.repository.UserRoleRepository;
import cn.springmvc.jpa.service.UserService;
import cn.springmvc.jpa.web.command.UserCommand;

/**
 * @author Wujun
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

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

        Role r = roleRepository.findRoleById(role.getId());
        if (r == null) {
            throw new BusinessException("user.registr.error", "用户未指定所属组织或角色");
        }

        entryptPassword(user);
        user.setStatus(Constants.STATUS_VALID);
        user.setCreateTime(Calendar.getInstance().getTime());
        userRepository.add(user);

        UserRole ur = new UserRole();
        ur.setRoleId(r.getId());
        ur.setUserId(user.getId());
        userRoleRepository.add(ur);
    }

    @Override
    public void updatePassword(UserCommand userCommand, User user) {
        if (log.isDebugEnabled()) {
            log.debug("## update User password.");
        }
        User u = userRepository.findUserById(user.getId());
        u.setPassword(userCommand.getPasswordAgain());
        entryptPassword(u);
        u.setModifyTime(Calendar.getInstance().getTime());
        userRepository.updatePassword(u);
    }

    @Override
    public User findLocalUserByName(String username) {
        return userRepository.findLocalUserByName(username);
    }

    @Override
    public void updateUserLastLoginTime(User user) {
        User u = userRepository.findUserById(user.getId());
        if (u != null) {
            u.setLastLoginTime(Calendar.getInstance().getTime());
            userRepository.updateUserLastLoginTime(u);
        }
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findUsers();
    }

    @Override
    public List<User> findEmp(String shopId, String empName) {
        return userRepository.findEmp(shopId, empName);
    }
}
