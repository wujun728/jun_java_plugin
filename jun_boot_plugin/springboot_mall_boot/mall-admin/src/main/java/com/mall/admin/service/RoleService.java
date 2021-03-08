package com.mall.admin.service;

import com.mall.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2422:11
 */
@Service
public class RoleService extends AdminBaseService {

    public List<Map> list(Map params) {
        return sqlSessionTemplate.selectList("system.role.list", params);
    }

    public List<Map> getRoleByUser(int userId) {
        return sqlSessionTemplate.selectList("system.role.getRoleByUser", userId);
    }
}
