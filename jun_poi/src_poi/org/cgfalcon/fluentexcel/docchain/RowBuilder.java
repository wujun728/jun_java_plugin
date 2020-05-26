package org.cgfalcon.fluentexcel.docchain;

import org.cgfalcon.fluentexcel.entity.DataCell;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:26
 */
public interface RowBuilder {

    RowBuilder fromCol(int col);
    RowBuilder addCell(DataCell cell);
    RowBuilder height(short height);

    CellBuilder createCell();

    BlockBuilder rowOver();

    int cellCount();
}
