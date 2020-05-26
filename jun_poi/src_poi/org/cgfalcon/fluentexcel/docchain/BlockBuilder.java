package org.cgfalcon.fluentexcel.docchain;

import org.cgfalcon.fluentexcel.entity.DataRow;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:25
 */
public interface BlockBuilder {

    BlockBuilder fromRow(int row);
    BlockBuilder addRow(DataRow row);

    RowBuilder createRow();

    SheetBuilder blockOver();

    int rowCount();
}
