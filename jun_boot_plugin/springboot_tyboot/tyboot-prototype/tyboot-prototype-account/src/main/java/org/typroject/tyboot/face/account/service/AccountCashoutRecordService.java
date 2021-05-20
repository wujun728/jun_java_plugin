package org.typroject.tyboot.face.account.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.Sequence;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.account.model.AccountCashoutRecordModel;
import org.typroject.tyboot.face.account.orm.dao.AccountCashoutRecordMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountCashoutRecord;
import org.typroject.tyboot.prototype.account.AccountManager;
import org.typroject.tyboot.prototype.account.AccountType;
import org.typroject.tyboot.prototype.account.DefaultAccountType;
import org.typroject.tyboot.prototype.account.trade.DefaultAccountTradeType;
import org.typroject.tyboot.prototype.account.trade.impl.FreezeHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;





/**
 * <p>
 * 提现申请表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Component
public class AccountCashoutRecordService extends BaseService<AccountCashoutRecordModel,AccountCashoutRecord,AccountCashoutRecordMapper>
{

    /**提现状态-待手工处理-大于指定金额的提现申请 需要后台审核之后才能实际转账*/
    public static final  String  CASHOUT_STATUS_SUSPEND = "SUSPEND";

    /**提现状态-待自动处理*/
    public static final  String  CASHOUT_STATUS_SUSPEND_AUTO = "SUSPEND_AUTO";

    /**提现状态-已转账*/
    public static final  String  CASHOUT_STATUS_TRANSFERRED = "TRANSFERRED";

    /**提现状态-处理中*/
    public static final  String  CASHOUT_STATUS_PENDING = "PENDING";

    /**提现状态-提现失败*/
    public static final  String  CASHOUT_STATUS_FAILED = "FAILED";





    public AccountCashoutRecordModel queryByBillNo(String billNo)throws Exception
    {
        return queryModelByParams(billNo);
    }


    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public AccountCashoutRecordModel createCashoutRecord(AccountCashoutRecordModel cashoutRecordModel, AccountType accountType)throws Exception
    {
        cashoutRecordModel.setUserId(RequestContext.getExeUserId());
        cashoutRecordModel.setApplayNo(Sequence.generatorBillNo());
        cashoutRecordModel.setApplyStatus(CASHOUT_STATUS_SUSPEND);
        //cashoutRecordModel.setOutAmount(cashoutRecordModel.getApplayAmount().subtract(cashoutRecordModel.getApplayAmount().multiply(TARIFF)));
        cashoutRecordModel.setOutAmount(cashoutRecordModel.getApplayAmount());
        //cashoutRecordModel.setPoundage(cashoutRecordModel.getApplayAmount().multiply(TARIFF));
        cashoutRecordModel.setPoundage(new BigDecimal(0));
        cashoutRecordModel.setApplayType("ALIPAY");

        //冻结资金
        freeze(cashoutRecordModel,accountType);

        return this.createWithModel(cashoutRecordModel);
    }


    private boolean freeze(AccountCashoutRecordModel cashoutRecordModel,AccountType accountType) throws Exception
    {

        AccountManager accountManager  = new AccountManager(cashoutRecordModel.getUserId(), accountType);

        Map<String,Object> params = new HashMap<>();
        params.put(FreezeHandler.FreezeParams.userId.getParamCode(),cashoutRecordModel.getUserId());
        params.put(FreezeHandler.FreezeParams.billNo.getParamCode(),cashoutRecordModel.getApplayNo());
        params.put(FreezeHandler.FreezeParams.amount.getParamCode(),cashoutRecordModel.getApplayAmount());
        params.put(FreezeHandler.FreezeParams.postscript.getParamCode(),"冻结");
        params.put(FreezeHandler.FreezeParams.transferType.getParamCode(),DefaultAccountTradeType.CASHOUT.name());
        return accountManager.executeTrade(DefaultAccountTradeType.FREEZE,params);
    }


    private boolean unfreeze(AccountCashoutRecordModel cashoutRecordModel,AccountType accountType) throws Exception
    {

        AccountManager accountManager  = new AccountManager(cashoutRecordModel.getUserId(), accountType);
        Map<String,Object> params = new HashMap<>();
        params.put(FreezeHandler.FreezeParams.userId.getParamCode(),cashoutRecordModel.getUserId());
        params.put(FreezeHandler.FreezeParams.billNo.getParamCode(),cashoutRecordModel.getApplayNo());
        params.put(FreezeHandler.FreezeParams.postscript.getParamCode(),"解冻");
        params.put(FreezeHandler.FreezeParams.transferType.getParamCode(),DefaultAccountTradeType.CASHOUT.name());
        return accountManager.executeTrade(DefaultAccountTradeType.UNFREEZE,params);
    }




    /**
     * 确认申请记录，
     * @param applyNo
     * @return
     * @throws Exception
     */
    public AccountCashoutRecordModel comfirm(String applyNo)throws Exception
    {
        AccountCashoutRecordModel cashoutRecordModel = this.queryByBillNo(applyNo);
        if(!ValidationUtil.isEmpty(cashoutRecordModel) && CASHOUT_STATUS_SUSPEND.equals(cashoutRecordModel.getApplyStatus()))
        {
            cashoutRecordModel.setApplyStatus(CASHOUT_STATUS_PENDING);
            this.updateWithModel(cashoutRecordModel);
        }else
        {
            throw new Exception("找不到指定的申请记录.");
        }



        return cashoutRecordModel;
    }


    /**
     * 转账确认，从冻结账户出账所申请的资金，将申请记录设置为体现成功
     * @param applyNo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public AccountCashoutRecordModel transferComfirm(String applyNo)throws Exception
    {
        AccountCashoutRecordModel cashoutRecordModel = this.queryByBillNo(applyNo);
        if(!ValidationUtil.isEmpty(cashoutRecordModel) && CASHOUT_STATUS_PENDING.equals(cashoutRecordModel.getApplyStatus()))
        {

            //将冻结的资金出账
            AccountManager accountManager  = new AccountManager(cashoutRecordModel.getUserId(), DefaultAccountType.FROZEN);
            accountManager.getAccountInstance().spend(cashoutRecordModel.getApplayAmount(),DefaultAccountTradeType.CASHOUT,cashoutRecordModel.getApplayNo());


            //将申请记录设置为提现成功
            cashoutRecordModel.setApplyStatus(CASHOUT_STATUS_TRANSFERRED);
            this.updateWithModel(cashoutRecordModel);
        }else
        {
            throw new Exception("找不到指定的申请记录.");
        }
        return cashoutRecordModel;
    }


    /**
     * 拒绝申请
     * @param applyNo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public AccountCashoutRecordModel refuse(String applyNo,AccountType accountType)throws Exception
    {
        AccountCashoutRecordModel cashoutRecordModel = this.queryByBillNo(applyNo);
        if(!ValidationUtil.isEmpty(cashoutRecordModel) && CASHOUT_STATUS_SUSPEND.equals(cashoutRecordModel.getApplyStatus()))
        {

            //将冻结的资金解冻
            this.unfreeze(cashoutRecordModel,accountType);

            cashoutRecordModel.setApplyStatus(CASHOUT_STATUS_FAILED);
            this.updateWithModel(cashoutRecordModel);
        }else
        {
            throw new Exception("找不到指定的申请记录.");
        }
        return cashoutRecordModel;
    }


    public Page queryForRecordPage(Page page,String applyStatus)throws Exception
    {
        return this.queryForPage(page,null,false,applyStatus);
    }






}
