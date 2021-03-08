package org.typroject.tyboot.prototype.trade;

import org.typroject.tyboot.core.foundation.exception.BaseException;

/**
 * Created by magintursh on 2018/3/14.
 */
public class TradeException extends BaseException {

    public TradeException(String message)
    {
        super(message, "TradeException", "交易错误.");
        this.httpStatus = 400;
    }
}
