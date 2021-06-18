package cn.springmvc.jpa.repository.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.UserRepository;

/**
 * @author Vincent.wang
 *
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public User findLocalUserByName(String username) {
        if (null == username || username.trim() == "") {
            log.warn("empty username used when trying to find local user.");
            return null;
        }
        String ql = "from cn.springmvc.jpa.entity.User u where u.name=:username";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        return baseRepository.queryOne(ql, params);
    }

    @Override
    public List<User> findUserByShop(String id) {
        String hql = "from cn.springmvc.jpa.entity.User where shopId=:shopId";
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("shopId", id);
        return baseRepository.query(hql, parameter);
    }

    @Override
    public void updateUserLastLoginTime(User user) {
        String ql = "update cn.springmvc.jpa.entity.User set lastLoginTime=:lastLoginTime where id=:id";
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("lastLoginTime", user.getLastLoginTime());
        parameter.put("id", user.getId());
        baseRepository.executeHqlUpdate(ql, parameter);
    }

    @Override
    public List<User> findUsers() {
        StringBuilder hql = new StringBuilder();
        hql.append(" select u from cn.springmvc.jpa.entity.User u , cn.springmvc.jpa.entity.UserRole ur , cn.springmvc.jpa.entity.Role r ");
        hql.append(" where u.id=ur.userId and ur.roleId=r.id ");
        hql.append(" and r.code=:roleCode ");
        hql.append(" and u.status=:status");
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("roleCode", Constants.COMMON_ROLE_CODE);
        parameter.put("status", Constants.STATUS_VALID);
        return baseRepository.query(hql.toString(), parameter);
    }

    @Override
    public List<User> findEmp(String shopId, String empName) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" select u from cn.springmvc.jpa.entity.User u , cn.springmvc.jpa.entity.UserRole ur , cn.springmvc.jpa.entity.Role r ");
        hql.append(" where u.id=ur.userId and ur.roleId=r.id ");
        hql.append(" and r.code=:roleCode ");
        hql.append(" and u.status=:status");
        parameter.put("roleCode", Constants.COMMON_ROLE_CODE);
        parameter.put("status", Constants.STATUS_VALID);
        if (StringUtils.isNotBlank(shopId)) {
            hql.append(" and u.organizeId=:organizeId");
            parameter.put("organizeId", shopId);
        }

        if (StringUtils.isNotBlank(empName)) {
            hql.append(" and u.trueName like :trueName");
            parameter.put("trueName", "%" + empName + "%");
        }
        return baseRepository.query(hql.toString(), parameter);
    }

    @Override
    public void add(User user) {
        baseRepository.save(user);
    }

    @Override
    public User findUserById(String id) {
        return baseRepository.getByPrimaryKey(User.class, id);
    }

    @Override
    public void updatePassword(User u) {
        baseRepository.saveOrUpdate(u);
    }

}