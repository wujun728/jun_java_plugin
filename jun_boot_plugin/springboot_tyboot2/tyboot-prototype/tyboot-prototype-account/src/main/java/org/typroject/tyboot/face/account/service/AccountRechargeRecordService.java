package org.typroject.tyboot.face.account.service;


import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.account.model.AccountRechargeRecordModel;
import org.typroject.tyboot.face.account.orm.dao.AccountRechargeRecordMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountRechargeRecord;

import java.math.BigDecimal;
import java.util.Date;





/**
 * <p>
 * 充值记录表，只记录交易成功的充值信息 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Component
public class AccountRechargeRecordService extends BaseService<AccountRechargeRecordModel,AccountRechargeRecord,AccountRechargeRecordMapper>
{


    public AccountRechargeRecordModel createRecord(String userId,String accountNo,BigDecimal rechargeAmount,String billNo,String accountType)throws Exception
    {
        AccountRechargeRecordModel newRecord = new AccountRechargeRecordModel();
        newRecord.setAccountNo(accountNo);
        newRecord.setAccountType(accountType);
        newRecord.setBillNo(billNo);
        newRecord.setRechargeAmount(rechargeAmount);
        newRecord.setRechargeTime(new Date());
        newRecord.setUserId(userId);
        return this.createWithModel(newRecord);
    }
}
