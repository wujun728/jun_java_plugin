package com.zhm.ssr.service.impl;

import com.zhm.ssr.dao.UserInfoDao;
import com.zhm.ssr.model.UserInfo;
import com.zhm.ssr.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhm on 2015/7/10.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo findByUserid(String userid) {
        return userInfoDao.findByUserid(userid);
    }

    @Cacheable(value={"userInfoCache"},
            key="T(com.zhm.ssr.utils.Md5Util).stringByMD5(#p0+'findUserInfoNumsByCond')")
    public long findNumsByCond(String keyword) {
        return userInfoDao.findNumsByCond(keyword);
    }

    /**
     * 缓存任意条件查询的前5页数据
     * @param keyword
     * @param order
     * @param offset
     * @param limit
     * @return
     */
    @Cacheable(value={"userInfoCache"},
            key="T(com.zhm.ssr.utils.Md5Util).stringByMD5(#p0+#p1+#p2+#p3+'findUserInfoByCond')",
            condition = "#p2/#p3<6"
    )
    public List<UserInfo> findByCond(String keyword, String order, int offset, int limit) {
        return userInfoDao.findByCond(keyword,order,offset,limit);
    }

    @CacheEvict(value="userInfoCache",allEntries = true)
    public void delInfoById(int id) {
        userInfoDao.delInfoById(id);
    }
}
