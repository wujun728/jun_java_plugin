package org.typroject.tyboot.face.account.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.account.model.AccountSerialModel;
import org.typroject.tyboot.face.account.orm.dao.AccountSerialMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountSerial;
import org.typroject.tyboot.prototype.account.AccountBaseOperation;
import org.typroject.tyboot.prototype.account.trade.AccountTradeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 虚拟账户金额变更记录表，所有针对账户金额的变动操作都要记录到此表中， 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Component
public class AccountSerialService extends BaseService<AccountSerialModel, AccountSerial, AccountSerialMapper> {

    public AccountSerialModel createAccountSerial(String userId, String accountNo, String accountType, Long updateVersion, String billNo, BigDecimal amount, AccountTradeType accountTradeType, AccountBaseOperation bookkeeping) throws Exception {
        //#1.查询当前流水记录
        AccountSerialModel lastAccountSerial = this.queryLastAccountSerial(accountNo);

        BigDecimal initialBalance = new BigDecimal(0);        //起始金额
        if (!ValidationUtil.isEmpty(lastAccountSerial)) {
            initialBalance = lastAccountSerial.getFinalBalance();
        }
        BigDecimal finalBalance = this.calaFinalBalance(bookkeeping, amount, initialBalance);//最终余额

        //#2.创建流水记录
        AccountSerialModel newAccountSerial = new AccountSerialModel();
        newAccountSerial.setAccountNo(accountNo);
        newAccountSerial.setBillNo(billNo);
        newAccountSerial.setChangeAmount(amount);
        newAccountSerial.setFinalBalance(finalBalance);
        newAccountSerial.setInitialPrefundedBalance(initialBalance);
        newAccountSerial.setOperateTime(new Date());
        newAccountSerial.setOperationType(accountTradeType.getAccountTradeType());
        newAccountSerial.setUserId(userId);
        newAccountSerial.setUpdateVersion(updateVersion);
        newAccountSerial.setAccountType(accountType);
        newAccountSerial.setBookkeeping(bookkeeping.name());
        return this.createWithModel(newAccountSerial);
    }

    public AccountSerialModel queryLastAccountSerial(String accountNo) throws Exception {

        AccountSerialModel accountSerialModel = null;
        List<AccountSerialModel> list = queryForTopList(1, "SEQUENCE_NBR" , false, accountNo);

        if (!ValidationUtil.isEmpty(list))
            accountSerialModel = list.get(0);
        return accountSerialModel;
    }


    /**
     * 计算最终余额
     *
     * @param bookkeeping
     * @param amount
     * @param initialBalance
     * @return
     */
    private BigDecimal calaFinalBalance(AccountBaseOperation bookkeeping, BigDecimal amount, BigDecimal initialBalance) throws Exception {
        BigDecimal finalBalance = new BigDecimal(0);
        switch (bookkeeping) {
            case INCOME:
                finalBalance = initialBalance.add(amount);
                break;
            case SPEND:
                finalBalance = initialBalance.subtract(amount);
                if (finalBalance.doubleValue() < 0) {
                    throw new BaseException("账户余额不足." , "" , "" );
                }
                break;
            default:
                throw new BaseException("账户操作类型有误." , "" , "" );
        }
        return finalBalance;

    }

    public Page querySerialPage(Page page, String accountNo, String accountType, String userId) throws Exception {
        return this.queryForPage(page,"OPERATE_TIME",false,accountNo,accountType,userId);
    }

}
