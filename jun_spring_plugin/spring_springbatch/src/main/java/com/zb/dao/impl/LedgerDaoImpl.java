package com.zb.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zb.dao.LedgerDao;
import com.zb.entity.batch.Ledger;

/**
 * ledger数据操作类
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:12:24
 */
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
@Repository
public class LedgerDaoImpl implements LedgerDao {

    private static final String SAVE_SQL = "insert into ledgers (rcv_dt, mbr_nm, chk_nbr, chk_dt, pymt_typ, dpst_amt, pymt_amt, comments) values(?,?,?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(final Ledger item) {
        System.out.println("save.........");
        jdbcTemplate.update(SAVE_SQL, new PreparedStatementSetter() {
            public void setValues(PreparedStatement stmt) throws SQLException {
                stmt.setDate(1, new java.sql.Date(item.getReceiptDate().getTime()));
                stmt.setString(2, item.getMemberName());
                stmt.setString(3, item.getCheckNumber());
                stmt.setDate(4, new java.sql.Date(item.getCheckDate().getTime()));
                stmt.setString(5, item.getPaymentType());
                stmt.setDouble(6, item.getDepositAmount());
                stmt.setDouble(7, item.getPaymentAmount());
                stmt.setString(8, item.getComments());
            }
        });
    }

    public LedgerDaoImpl() {
    }
}
