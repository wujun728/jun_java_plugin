package com.zb.batch.writer;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.zb.entity.batch.Ledger;

/**
 * ledger写入数据
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:11:14
 */
@Component("ledgerWriter")
public class LedgerWriter implements ItemWriter<Ledger> {

    /**
     * 写入数据
     * 
     * @param ledgers
     */
    public void write(List<? extends Ledger> ledgers) throws Exception {
        System.out.println("查询数据开始...........");
        for (Ledger ledger : ledgers) {
            System.out.println("------------" + ledger.toString());
        }
        System.out.println("查询数据结束...........");
    }
}