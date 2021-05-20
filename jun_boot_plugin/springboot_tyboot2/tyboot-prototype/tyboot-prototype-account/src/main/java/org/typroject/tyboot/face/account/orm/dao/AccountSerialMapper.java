package org.typroject.tyboot.face.account.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;;
import org.typroject.tyboot.face.account.orm.entity.AccountSerial;

/**
 * <p>
  * 虚拟账户金额变更记录表，所有针对账户金额的变动操作都要记录到此表中， Mapper 接口
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
public interface AccountSerialMapper extends BaseMapper<AccountSerial> {

}