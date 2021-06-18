package com.zb.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.zb.entity.batch.Ledger;

public class UserMapper implements FieldSetMapper<Ledger> {

    @Override
    public Ledger mapFieldSet(FieldSet fs) throws BindException {
        /*
         * User u = new User(); u.setName(fs.readString(0));
         * u.setAge(fs.readInt(1));
         */

        Ledger ledger = new Ledger();
        ledger.setReceiptDate(fs.readDate(0));
        ledger.setMemberName(fs.readString(1));
        ledger.setCheckNumber(fs.readString(2));
        ledger.setCheckDate(fs.readDate(3));
        ledger.setPaymentType(fs.readString(4));
        ledger.setDepositAmount(fs.readDouble(5));
        ledger.setPaymentAmount(fs.readDouble(6));
        ledger.setComments(fs.readString(7));
        return ledger;
    }

}
