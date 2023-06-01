package com.cosmoplat.service.sys.impl;

import com.cosmoplat.entity.sys.SysDept;
import com.cosmoplat.service.sys.DeptProviderService;
import com.cosmoplat.service.sys.DeptService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/26
 * Description: No Description
 */
@Service
public class DeptServiceImpl implements DeptService {

    @DubboReference
    DeptProviderService deptProviderService;

    @Override
    public Object addDept(SysDept vo) {
        return deptProviderService.addDept(vo);
    }

    @Override
    public void deleted(String id) {
        deptProviderService.deleted(id);
    }

    @Override
    public void updateDept(SysDept vo) {
        deptProviderService.updateDept(vo);
    }

    @Override
    public Object getById(String id) {
        return deptProviderService.getById(id);
    }

    @Override
    public Object deptTreeList(String deptId) {
        return deptProviderService.deptTreeList(deptId);
    }

    @Override
    public Object selectAll() {
        return deptProviderService.selectAll();
    }
}
