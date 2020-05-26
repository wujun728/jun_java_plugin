package org.cgfalcon.fluentexcel.docchain.impl;

import org.cgfalcon.fluentexcel.docchain.BlockBuilder;
import org.cgfalcon.fluentexcel.docchain.CellBuilder;
import org.cgfalcon.fluentexcel.docchain.RowBuilder;
import org.cgfalcon.fluentexcel.entity.DataCell;
import org.cgfalcon.fluentexcel.entity.DataRow;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:55
 */
public class BaseRowBuilder implements RowBuilder {

    private BlockBuilder blockBuilder;
    private DataRow dataRow = new DataRow();

    public BaseRowBuilder(BlockBuilder blockBuilder) {
        this.blockBuilder = blockBuilder;
    }

    @Override
    public RowBuilder fromCol(int col) {
        dataRow.setStartCol(col);
        return this;
    }

    @Override
    public RowBuilder addCell(DataCell cell) {
        dataRow.addCell(cell);
        return this;
    }

    @Override
    public RowBuilder height(short height) {
        dataRow.setHeight(height);
        return this;
    }

    @Override
    public CellBuilder createCell() {
        return new BaseCellBuilder(this);
    }

    @Override
    public BlockBuilder rowOver() {
        blockBuilder.addRow(dataRow);
        return blockBuilder;
    }

    @Override
    public int cellCount() {
        return dataRow.getCells().size();
    }
}
