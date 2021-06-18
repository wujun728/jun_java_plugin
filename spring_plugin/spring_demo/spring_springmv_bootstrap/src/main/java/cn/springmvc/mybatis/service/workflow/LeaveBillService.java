package cn.springmvc.mybatis.service.workflow;

import java.util.List;

import cn.springmvc.mybatis.entity.activiti.LeaveBill;

public interface LeaveBillService {

    List<LeaveBill> findLeaveBillList();

    void saveLeaveBill(LeaveBill leaveBill);

    LeaveBill findLeaveBillById(Long id);

    void deleteLeaveBillById(Long id);

}
