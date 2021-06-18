package cn.springmvc.mybatis.service.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.mybatis.entity.activiti.LeaveBill;
import cn.springmvc.mybatis.mapper.activiti.LeaveBillMapper;
import cn.springmvc.mybatis.service.workflow.LeaveBillService;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {

    @Autowired
    private LeaveBillMapper leaveBillMapper;

    // @Autowired
    // private EmployeeMapper employeeMapper;

    /** 查询自己的请假单的信息 */
    @Override
    public List<LeaveBill> findLeaveBillList() {
        List<LeaveBill> list = leaveBillMapper.findAll();
        return list;
    }

    /** 保存请假单 */
    @Override
    public void saveLeaveBill(LeaveBill leaveBill) {
        // 获取请假单ID
        Long id = leaveBill.getId();
        /** 新增保存 */
        if (id == null) {
            // 1：从Session中获取当前用户对象，将LeaveBill对象中user与Session中获取的用户对象进行关联
            // leaveBill.setUser(SessionContext.get());// 建立管理关系g
            // employeeMapper.insert(leaveBill.getUserId());
            // 保存user用户
            // 2：保存请假单表，添加一条数据
            leaveBillMapper.saveLeaveBill(leaveBill);
        }
        /** 更新保存 */
        else {
            // 1：执行update的操作，完成更新
            leaveBillMapper.updateLeaveBill(leaveBill);
        }

    }

    /** 使用请假单ID，查询请假单的对象 */
    @Override
    public LeaveBill findLeaveBillById(Long id) {
        LeaveBill bill = leaveBillMapper.findById(id);
        return bill;
    }

    /** 使用请假单ID，删除请假单 */
    @Override
    public void deleteLeaveBillById(Long id) {
        leaveBillMapper.delete(id);
    }

}
