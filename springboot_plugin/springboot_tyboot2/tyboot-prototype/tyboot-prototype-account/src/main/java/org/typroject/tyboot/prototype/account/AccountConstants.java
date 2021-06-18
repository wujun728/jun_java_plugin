package org.typroject.tyboot.prototype.account;

/**
 * Created by magintursh on 2016-12-12.
 */
public class AccountConstants {




    /**默认支付密码*/
    public static final String DEFAULT_PAYMENT_PASSWORD = "111111";


    /**字典编号--资金管理*/
    public  static final String DICT_CODE_FINANCE = "FINANCE";
    /**字典key--提现手续费比例*/
    public static final String DICT_KEY_CASHOUT_POUNDAGE = "CASHOUT_POUNDAGE";

    /**字典key--最低提现金额限制*/
    public static final String DICT_KEY_CASHOUT_LIMIT = "CASHOUT_LIMIT";





    /**提现状态-待手工处理-大于指定金额的提现申请 需要后台审核之后才能实际转账*/
    public static final  String  CASHOUT_STATUS_SUSPEND = "suspend";

    /**提现状态-待自动处理*/
    public static final  String  CASHOUT_STATUS_SUSPEND_AUTO = "suspend_auto";

    /**提现状态-已转账*/
    public static final  String  CASHOUT_STATUS_TRANSFERRED = "transferred";

    /**提现状态-处理中*/
    public static final  String  CASHOUT_STATUS_PENDING = "pending";

    /**提现状态-提现失败*/
    public static final  String  CASHOUT_STATUS_FAILED = "failed";



    public static final String NOTFOUNT_ACCOUNT_HANDLER = "账户交易处理器未找到.";


    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";


}
