package org.typroject.tyboot.prototype.account.trade;

import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/6.
 */
public class BaseTradeParams {

    /**
     * 解析和验证交易参数完整性
     * 1.解析参数列表
     * 2.检查参数完整性
     * 3.设置将要操作的账户信息
     * @param params
     * @return
     * @throws Exception
     */
    public static     boolean checkPrams(Map<String, Object> params, TradeParams[] tradeParams) throws Exception
    {
        boolean flag;
        //验证必填参数
        if(!ValidationUtil.isEmpty(params))
        {
            for(TradeParams tradeparam: tradeParams)
            {
                if(tradeparam.isNotnull() && ValidationUtil.isEmpty(params.get(tradeparam.getParamCode())) )
                {
                    throw new Exception("交易参数："+tradeparam.getParamCode()+";"+tradeparam.getParesStr() +"不能为空");
                }
            }
            flag = true;
        }else{
            throw new Exception("参数列表不能为空.");
        }
        return flag;
    }

}
