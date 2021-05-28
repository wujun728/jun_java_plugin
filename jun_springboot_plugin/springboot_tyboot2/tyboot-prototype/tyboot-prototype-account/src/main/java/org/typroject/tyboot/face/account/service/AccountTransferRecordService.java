package org.typroject.tyboot.face.account.service;


import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.account.model.AccountTransferRecordModel;
import org.typroject.tyboot.face.account.orm.dao.AccountTransferRecordMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountTransferRecord;

import java.math.BigDecimal;
import java.util.Date;





/**
 * <p>
 * 账户转账记录:
特指：内部账户之间主动转账，冻结/解冻资金所引发的转账到冻结账户，内部账户到外部账户的主动转账 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-24
 */
@Component
public class AccountTransferRecordService extends BaseService<AccountTransferRecordModel,AccountTransferRecord,AccountTransferRecordMapper>
{

    public static final String TRANSFERSTATUS_SUCCESS = "SUCCESS";


      public AccountTransferRecordModel queryRecord(String  userId,String sourceAccountNo,String billNo,String transferType)throws Exception
      {
          return this.queryModelByParams(userId,sourceAccountNo,billNo,transferType);
      }


      public AccountTransferRecordModel saveTransferRecord(String  userId, String billNo, String sourceAccountNo, String sourceAccountType,
                                                           String targetAccountNo, String targetAccountType, BigDecimal amount, String postScript, String transferType) throws Exception
      {
          AccountTransferRecordModel record = new AccountTransferRecordModel();
          record.setBillNo(billNo);
          record.setSourceAccountNo(sourceAccountNo);
          record.setSourceAccountType(sourceAccountType);
          record.setTargetAccountNo(targetAccountNo);
          record.setTargetAccountType(targetAccountType);
          record.setTransferAmount(amount);
          record.setTransferPostscript(postScript);
          record.setTransferStatus(TRANSFERSTATUS_SUCCESS);
          record.setTransferTime(new Date());
          record.setTransferType(transferType);
          record.setUserId(userId);
          return this.createWithModel(record);
      }



}
