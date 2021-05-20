package org.typroject.tyboot.face.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.trade.model.TransactionsRecordModel;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.face.trade.orm.dao.TransactionsRecordMapper;
import org.typroject.tyboot.face.trade.orm.entity.TransactionsRecord;

import java.util.Date;

/**
 * <p>
 * 交易记录表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Component
public class TransactionsRecordService extends BaseService<TransactionsRecordModel, TransactionsRecord, TransactionsRecordMapper> {


    /**
     * 分页查询
     */
    public Page queryForPage(Page page, String agencyCode) throws Exception {
      return this.queryForPage(page,"",false,agencyCode);
    }


    public TransactionsRecordModel selectBillNo(String billNo) throws Exception {
        return this.queryModelByParams(billNo);
    }

    public TransactionsRecordModel selectSerialNo(String serialNo) throws Exception {
        return queryModelByParams(serialNo);
    }


    public TransactionsRecordModel createTransactionsRecord(TransactionsSerialModel serialModel) throws Exception {

        TransactionsRecordModel record = this.selectBillNo(serialModel.getBillNo());
        if (ValidationUtil.isEmpty(record)) {
            record = new TransactionsRecordModel();
            record.setAgencyCode(serialModel.getAgencyCode());
            record.setBillNo(serialModel.getBillNo());
            record.setFinishedTime(new Date());
            record.setPayMethod(serialModel.getPayMethod());
            record.setSerialNo(serialModel.getSerialNo());
            record.setTradeAmount(serialModel.getTradeAmount());
            record.setTradeType(serialModel.getTradeType());
            record.setUserId(serialModel.getUserId());
            record.setBillType(serialModel.getBillType());
            this.createWithModel(record);
        } else {
            throw new Exception("重复的交易记录." );
        }
        return record;
    }


}
