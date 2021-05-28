package com.chentongwei.core.common.dao;

import com.chentongwei.core.common.entity.vo.RegionVO;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区DAO
 */
public interface IRegionDAO {

    /**
     * 根据pid查询省市区
     *
     * @param pid：父id
     * @return
     */
    List<RegionVO> listByPid(Integer pid);

}
