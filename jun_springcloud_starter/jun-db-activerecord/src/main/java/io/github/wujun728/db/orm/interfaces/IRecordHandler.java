package io.github.wujun728.db.orm.interfaces;

import cn.hutool.core.util.ObjUtil;
import io.github.wujun728.db.record.Record;

import java.util.Objects;

public interface IRecordHandler {

     String tableName() ;

    default boolean openInsertFill() {
        return true;
    }

    default boolean openUpdateFill() {
        return true;
    }

    void insertFill(Record record);

    void updateFill(Record record);

    default void setFieldValByName(String fieldName, Object fieldVal, Record record) {
        if (Objects.nonNull(fieldVal) && ObjUtil.isNotNull(record.get(fieldName))) {
            record.set(fieldName, fieldVal);
        }
    }

}
