package org.cgfalcon.fluentexcel.docchain;

import org.cgfalcon.fluentexcel.entity.DataSheet;
import org.cgfalcon.fluentexcel.render.Render;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:03
 */
public interface DocBuilder {

    DocBuilder withDocRender(Render render);
    DocBuilder type(String docType);
    DocBuilder docName(String docName);
    DocBuilder addSheet(DataSheet sheet);
    /**
     *
     * tell fluentexcel where to produce excel
     * */
    DocBuilder saveTo(String saveDir);

    SheetBuilder createSheet();

    String rendDoc();

    int sheetCount();

}
