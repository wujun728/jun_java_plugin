package com.cosmoplat.service.sys;

import com.cosmoplat.entity.sys.SysDept;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/26
 * Description: No Description
 */
public interface DeptService {
    Object addDept(SysDept vo);

    void deleted(String id);

    void updateDept(SysDept vo);

    Object getById(String id);

    Object deptTreeList(String deptId);

    Object selectAll();
}
