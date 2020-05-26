package org.cgfalcon.fluentexcel.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: falcon.chu
 * Date: 13-5-31
 * Time: 下午3:39
 */
public class DataSheet {

    private String sheetName = "Sheet1";
    private Map<Integer, Integer> colWidth = new HashMap<Integer, Integer>();
    private List<MergerRegion> mergerRegions = new ArrayList<MergerRegion>();

    public List<DataBlock> blocks;

    public List<MergerRegion> getMergerRegions() {
        return mergerRegions;
    }

    public void setMergerRegions(List<MergerRegion> mergerRegions) {
        this.mergerRegions = mergerRegions;
    }

    public Map<Integer, Integer> getColWidth() {
        return colWidth;
    }

    public void setColWidth(Map<Integer, Integer> colWidth) {
        this.colWidth = colWidth;
    }

    public String getSheetName() {
        return sheetName == null ? "" : sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<DataBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<DataBlock> blocks) {
        this.blocks = blocks;
    }

    public void addBlock(DataBlock block) {
        if (blocks == null) {
            blocks = new ArrayList<DataBlock>();
        }
        blocks.add(block);
    }

    public void addMergerRegion(MergerRegion newMergerRegion) {
        if (mergerRegions == null) {
            mergerRegions = new ArrayList<MergerRegion>();
        }
        mergerRegions.add(newMergerRegion);
    }
}
