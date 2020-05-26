package org.cgfalcon.fluentexcel.entity;

/**
 * User: falcon.chu
 * Date: 13-9-6
 * Time: 下午2:45
 */
public class MergerRegion {

    /**
     *
     * 合并区起始行
    * */
    private int fromRow;

    /**
     * 合并区结束行
     */
    private int toRow;

    /**
     * 合并区起始列
     */
    private int fromCol;

    /**
     * 合并区结束列
     */
    private int toCol;

    public MergerRegion(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromCol = fromCol;
        this.fromRow = fromRow;
        this.toCol = toCol;
        this.toRow = toRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public void setFromCol(int fromCol) {
        this.fromCol = fromCol;
    }

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getToCol() {
        return toCol;
    }

    public void setToCol(int toCol) {
        this.toCol = toCol;
    }

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }
}
