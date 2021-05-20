package com.zb.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.dao.LedgerDao;
import com.zb.entity.batch.Ledger;

/**
 * 
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:11:26
 */
@Component("messageWriter")
public class MessageItemWriter implements ItemWriter<Ledger> {
    @Autowired
    private LedgerDao ledgerDao;

    /**
     * 写入数据
     * 
     * @param ledgers
     */
    @Override
    public void write(List<? extends Ledger> ledgers) throws Exception {
        System.out.println("写数据开始...........");
        for (Ledger ledger : ledgers) {
            System.out.println("------------------" + ledger.toString()
                    + "-----------------");
            ledgerDao.save(ledger);
            // System.out.println("------------"+ledger.getComments());
        }
        System.out.println("写数据结束...........");
    }
}
