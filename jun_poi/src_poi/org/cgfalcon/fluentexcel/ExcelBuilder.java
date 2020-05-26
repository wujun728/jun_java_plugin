package org.cgfalcon.fluentexcel;

import org.cgfalcon.fluentexcel.docchain.DocBuilder;
import org.cgfalcon.fluentexcel.docchain.impl.BaseDocBuilder;
import org.cgfalcon.fluentexcel.style.CellStyleBuilder;
import org.cgfalcon.fluentexcel.style.impl.BaseCellStyleBuilder;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午5:19
 */
public class ExcelBuilder {

    public ExcelBuilder() {

    }

    public DocBuilder createDoc() {
        return new BaseDocBuilder();
    }

    public CellStyleBuilder createCellStyle() {
        return new BaseCellStyleBuilder();
    }
}
