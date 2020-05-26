package org.cgfalcon.fluentexcel.docchain.impl;

import org.cgfalcon.fluentexcel.docchain.BlockBuilder;
import org.cgfalcon.fluentexcel.docchain.RowBuilder;
import org.cgfalcon.fluentexcel.docchain.SheetBuilder;
import org.cgfalcon.fluentexcel.entity.DataBlock;
import org.cgfalcon.fluentexcel.entity.DataRow;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:50
 */
public class BaseBlockBuilder implements BlockBuilder {

    private SheetBuilder sheetBuilder;
    private DataBlock dataBlock = new DataBlock();

    public BaseBlockBuilder(SheetBuilder sheetBuilder) {
        this.sheetBuilder = sheetBuilder;
    }

    @Override
    public BlockBuilder fromRow(int row) {
        dataBlock.setStartRow(row);
        return this;
    }

    @Override
    public BlockBuilder addRow(DataRow row) {
        dataBlock.addRow(row);
        return this;
    }

    @Override
    public RowBuilder createRow() {
        return new BaseRowBuilder(this);
    }

    @Override
    public SheetBuilder blockOver() {
        sheetBuilder.addBlock(dataBlock);
        return sheetBuilder;
    }

    @Override
    public int rowCount() {
        return dataBlock.getRows().size();
    }
}
