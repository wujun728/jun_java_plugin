package org.typroject.tyboot.core.auth.face.service;


import org.springframework.stereotype.Service;
import org.typroject.tyboot.core.auth.face.model.LoginHistoryModel;
import org.typroject.tyboot.core.auth.face.orm.dao.LoginHistoryMapper;
import org.typroject.tyboot.core.auth.face.orm.entity.LoginHistory;

import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.List;

/**
 * <p>
 * 用户登录记录 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-07-06
 */
@Service
public class LoginHistoryService extends BaseService<LoginHistoryModel,LoginHistory, LoginHistoryMapper> {




    public LoginHistoryModel createLoginHistory(LoginHistoryModel model)
    {
        this.createWithModel(model);
        return model;
    }


    // 获取最后一个记录
    public LoginHistoryModel queryLastHistr( String userId, String loginId)
    {
        LoginHistoryModel  model = null;
        List<LoginHistoryModel>  list = this.queryForList("SESSION_CREATE_TIME",false,userId,loginId);

        if(!ValidationUtil.isEmpty(list))
        {
            model = list.get(0);
        }
        return model;
    }


    public LoginHistoryModel queryByToken(String token)
    {
        return this.queryModelByParamsWithCache(token);
    }


	
}
