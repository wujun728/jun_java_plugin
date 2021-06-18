package com.zb.batch.writer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zb.entity.batch.Ledger;

/**
 * ledger行的映射类
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:11:00
 */
@SuppressWarnings("rawtypes")
@Component("ledgerRowMapper")
public class LedgerRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ledger ledger = new Ledger();
        ledger.setId(rs.getInt("id"));
        ledger.setReceiptDate(rs.getDate("rcv_dt"));
        ledger.setMemberName(rs.getString("mbr_nm"));
        ledger.setCheckNumber(rs.getString("chk_nbr"));
        ledger.setCheckDate(rs.getDate("chk_dt"));
        ledger.setPaymentType(rs.getString("pymt_typ"));
        ledger.setDepositAmount(rs.getDouble("dpst_amt"));
        ledger.setPaymentAmount(rs.getDouble("pymt_amt"));
        ledger.setComments(rs.getString("comments"));
        return ledger;
    }
}
