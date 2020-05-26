package org.cgfalcon.fluentexcel.docchain;

import org.cgfalcon.fluentexcel.entity.DataBlock;
import org.cgfalcon.fluentexcel.entity.MergerRegion;

import java.util.Map;

/**
 * User: falcon.chu
 * Date: 13-9-10
 * Time: 下午3:20
 */
public interface SheetBuilder {

    SheetBuilder sheetName(String sheetName);
    SheetBuilder withColWidth(Map<Integer, Integer> colWidth);
    SheetBuilder addMergerRegion(MergerRegion mergerRegion);
    SheetBuilder addBlock(DataBlock block);

    BlockBuilder createBlock();

    DocBuilder sheetOver();

    int blockCount();


}
