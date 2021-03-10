package com.chentongwei.core.common.dao;

import com.chentongwei.core.common.entity.io.ExistsOneSelfIO;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 通用DAO层
 **/
public interface ICommonDAO {

    /**
     * 判断是否存在
     *
     * @param existsOneSelfIO：参数
     * @return
     */
    Integer existsOneSelf(ExistsOneSelfIO existsOneSelfIO);

}
