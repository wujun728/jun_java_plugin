package com.lxhc.service;

import com.lxhc.model.Emp;
import com.lxhc.model.EmpBalance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2020/5/21.
 *
 * @author 拎小壶冲
 */
@Service
public class EmpBalanceService {

    @Transactional(transactionManager="mainDataSourceTransactionManager", rollbackFor = Exception.class)
    public void addBalance() {
        // 这个应该不能保存进表中才对
        new EmpBalance().set("emp_id", 1)
                .set("balance", 10).save();

        Emp emp = Emp.dao.findById(1);

        // 这里故意写错，以测试实务
        emp.set("name", "超出长度的字符串用于测试错误").update();
    }
}
