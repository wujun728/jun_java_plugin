package org.typroject.tyboot.face.account.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.constans.CoreConstans;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.account.model.AccountInfoModel;
import org.typroject.tyboot.face.account.orm.dao.AccountInfoMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountInfo;
import org.typroject.tyboot.prototype.account.AccountBaseOperation;
import org.typroject.tyboot.prototype.account.AccountConstants;
import org.typroject.tyboot.prototype.account.AccountStatus;
import org.typroject.tyboot.prototype.account.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 用户虚拟账户表，记录所有公网用户的虚拟账户，account_no字段预留，用以多账户支持 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Component
public class AccountInfoService extends BaseService<AccountInfoModel, AccountInfo, AccountInfoMapper> {


    @Autowired
    private Sequence sequence;

    public AccountInfoModel initAccountInfo(String userId, AccountType accountType, String accountNo) throws Exception {
        AccountInfoModel accountInfo = new AccountInfoModel();
        accountInfo.setAccountNo(accountNo);//账户编号生成
        accountInfo.setUserId(userId);
        accountInfo.setAccountType(accountType.getAccountType());
        accountInfo.setAccountStatus(AccountStatus.NORMAL.name());
        accountInfo.setAgencyCode(CoreConstans.CODE_SUPER_ADMIN);
        accountInfo.setBalance(new BigDecimal(0));
        accountInfo.setCreateTime(new Date());
        accountInfo.setCumulativeBalance(new BigDecimal(0));
        accountInfo.setPaymentPassword(AccountConstants.DEFAULT_PAYMENT_PASSWORD);
        accountInfo.setUpdateVersion(sequence.nextId());
        return this.createWithModel(accountInfo);
    }


    public AccountInfoModel updateFinalBalance(String accountNo, BigDecimal changeAmount, Long oldUpdateVersion, AccountBaseOperation bookkeeping) throws Exception {
        AccountInfoModel oldModel = this.queryByAccontNo(accountNo, oldUpdateVersion);
        if (!ValidationUtil.isEmpty(oldModel)) {
            if (AccountBaseOperation.INCOME.equals(bookkeeping)) {
                oldModel.setBalance(oldModel.getBalance().add(changeAmount));
                oldModel.setCumulativeBalance(oldModel.getCumulativeBalance().add(changeAmount));
            }
            if (AccountBaseOperation.SPEND.equals(bookkeeping))
                oldModel.setBalance(oldModel.getBalance().subtract(changeAmount));

            oldModel.setUpdateVersion(sequence.nextId());
            oldModel.setRecDate(new Date());
            oldModel.setRecUserId(RequestContext.getExeUserId());


            Map<String, Object> params = new HashMap<>();
            params.put("UPDATE_VERSION" , oldUpdateVersion);
            params.put("ACCOUNT_NO" , accountNo);
            params.put("ACCOUNT_STATUS" , AccountStatus.NORMAL.name());
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.allEq(params, false);
            boolean result = this.update(Bean.toPo(oldModel, new AccountInfo()), wrapper);
            if (!result)
                throw new Exception("更新余额失败." );
        } else {
            throw new Exception("找不到指定账户." );
        }
        return oldModel;
    }


    public AccountInfoModel updateAccountStatus(String accountNo, AccountStatus newStatus, AccountStatus oldStatus, Long oldUpdateVersion) throws Exception {
        AccountInfoModel oldModel = this.queryByAccontNo(accountNo, oldUpdateVersion);
        if (!ValidationUtil.isEmpty(oldModel)) {
            oldModel.setAccountStatus(newStatus.name());
            oldModel.setRecDate(new Date());
            oldModel.setRecUserId(RequestContext.getExeUserId());

            UpdateWrapper wrapper = new UpdateWrapper();
            wrapper.eq("UPDATE_VERSION" , oldUpdateVersion);
            wrapper.eq("ACCOUNT_NO" , accountNo);
            wrapper.eq("PAYMENT_PASSWORD" , AccountConstants.DEFAULT_PAYMENT_PASSWORD);
            if (!ValidationUtil.isEmpty(oldStatus))
                wrapper.eq("ACCOUNT_STATUS" , oldStatus.name());
            boolean result = this.update(Bean.toPo(oldModel, new AccountInfo()), wrapper);
            if (!result)
                throw new Exception("更新状态失败." );
        } else {
            throw new Exception("找不到指定账户." );
        }
        return oldModel;
    }


    public AccountInfoModel queryByAccontNo(String accountNo) throws Exception {
        return this.queryModelByParams(accountNo);
    }

    public AccountInfoModel queryByAccontNo(String accountNo, Long updateVersion) throws Exception {
       return this.queryModelByParams(accountNo,updateVersion);
    }

    public AccountInfoModel queryByUserId(String userId, AccountType accountType) throws Exception {
        AccountInfoModel accountInfoModel = new AccountInfoModel();
        accountInfoModel.setUserId(userId);
        accountInfoModel.setAccountType(accountType.getAccountType());
        return this.queryByModel(accountInfoModel);
    }
}
