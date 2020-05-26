package org.cgfalcon.fluentexcel.docchain.impl;

import org.apache.poi.ss.usermodel.CellStyle;
import org.cgfalcon.fluentexcel.docchain.CellBuilder;
import org.cgfalcon.fluentexcel.docchain.RowBuilder;
import org.cgfalcon.fluentexcel.entity.DataCell;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午4:00
 */

public class BaseCellBuilder implements CellBuilder {

    private RowBuilder rowBuilder;
    private DataCell dataCell = new DataCell();

    public BaseCellBuilder(RowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }

    @Override
    public CellBuilder type(int cellType) {
        dataCell.setType(cellType);
        return this;
    }

    @Override
    public CellBuilder withStyle(CellStyle cellStyle) {
        dataCell.setCss(cellStyle);
        return this;
    }

    @Override
    public CellBuilder withStyle(String styleJson) {
        dataCell.setRawStyle(styleJson);
        return this;
    }

    @Override
    public CellBuilder content(String content) {
        dataCell.setContent(content);
        return this;
    }

    @Override
    public RowBuilder cellOver() {
        rowBuilder.addCell(dataCell);
        return rowBuilder;
    }
}
