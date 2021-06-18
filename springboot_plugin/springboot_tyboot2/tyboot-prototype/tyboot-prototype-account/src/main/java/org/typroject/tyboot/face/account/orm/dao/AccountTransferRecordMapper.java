package org.typroject.tyboot.face.account.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.typroject.tyboot.face.account.orm.entity.AccountTransferRecord;

/**
 * <p>
  * 账户转账记录:
特指：内部账户之间主动转账，冻结/解冻资金所引发的转账到冻结账户，内部账户到外部账户的主动转账 Mapper 接口
 * </p>
 *
 * @author Wujun
 * @since 2018-01-24
 */
public interface AccountTransferRecordMapper extends BaseMapper<AccountTransferRecord> {

}