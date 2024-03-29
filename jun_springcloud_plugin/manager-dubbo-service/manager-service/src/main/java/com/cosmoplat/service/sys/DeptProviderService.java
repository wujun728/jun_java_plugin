package com.cosmoplat.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysDept;
import com.cosmoplat.entity.sys.vo.resp.DeptRespNodeVO;

import java.util.List;

/**
 * 部门
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface DeptProviderService extends IService<SysDept> {

    SysDept addDept(SysDept vo);

    void updateDept(SysDept vo);

    void deleted(String id);

    List<DeptRespNodeVO> deptTreeList(String deptId);

    List<SysDept> selectAll();
}
