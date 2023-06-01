package cn.springmvc.mybatis.mapper.activiti;

import java.util.List;

import cn.springmvc.mybatis.entity.activiti.LeaveBill;
import cn.springmvc.mybatis.mapper.BaseMapper;

public interface LeaveBillMapper extends BaseMapper<Long, LeaveBill> {

    public List<LeaveBill> findAll();

    public void saveLeaveBill(LeaveBill leaveBill);

    public void updateLeaveBill(LeaveBill leaveBill);

}
