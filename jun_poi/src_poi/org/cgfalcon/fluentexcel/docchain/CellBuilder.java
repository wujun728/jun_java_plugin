package org.cgfalcon.fluentexcel.docchain;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:27
 */
public interface CellBuilder {

    CellBuilder type(int cellType);
    CellBuilder withStyle(CellStyle cellStyle);
    CellBuilder withStyle(String styleJson);
    CellBuilder content(String content);

    RowBuilder cellOver();
}
