package com.chentongwei.core.user.util;

import com.chentongwei.common.enums.IBaseEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.core.user.enums.msg.UserMsgEnum;
import com.chentongwei.core.user.enums.status.UserActiveStatusEnum;
import com.chentongwei.core.user.enums.status.UserDisabledStatusEnum;

import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户部分异常处理
 */
public final class UserExceptionUtil {


    /**
     * 判断用户是否激活
     *
     * @param activeStatus：用户状态 0未激活 1已激活
     */
    public static final void userActiveCheck(final Integer activeStatus) {
        if(Objects.equals(activeStatus, UserActiveStatusEnum.USER_IS_NOT_ACTIVE.value())) {
            throwBussinessException(UserMsgEnum.USER_NOT_ACTIVE);
        }
    }

    /**
     * 判断用户是否已经激活过
     *
     * @param activeStatus：用户状态 0未激活 1已激活
     */
    public static final void alreadyActiveCheck(final Integer activeStatus) {
        if(Objects.equals(activeStatus, UserActiveStatusEnum.USER_IS_ACTIVE.value())) {
            throwBussinessException(UserMsgEnum.USER_ALREADY_ACTIVE);
        }
    }

    /**
     * 判断用户是否禁用
     *
     * @param disabledStatus：是否禁用 0禁用 1未禁用
     */
    public static final void userDisabledCheck(final Integer disabledStatus) {
        if(Objects.equals(disabledStatus, UserDisabledStatusEnum.USER_DISABLED.value())) {
            throwBussinessException(UserMsgEnum.USER_IS_DISABLED);
        }
    }

    /**
     * 抛出业务异常
     *
     * @param baseEnum 枚举常量
     */
    private static final void throwBussinessException(final IBaseEnum baseEnum) {
        throw new BussinessException(baseEnum);
    }
}
