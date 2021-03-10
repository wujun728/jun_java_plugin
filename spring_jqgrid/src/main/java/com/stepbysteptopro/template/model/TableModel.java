package com.stepbysteptopro.template.model;

import java.util.List;

/**
 * 
 * @author Wujun
 *
 */
public class TableModel<T> {
    private int pageNumber;
    private int pagesAmount;
    private int recordsTotalAmount;
    private int fromIndex;
    private int toIndex;
    private List<T> rows;
    
    /**
     * Constructor for creation of table model.
     * 
     * @param pageNumber the current page number selected by user
     * @param rowsAmountLimit the amount rows that we want to see
     * @param recordsTotalAmount the total amount of all records
     * @param sortedColumnIndex the index of column which we are going to sort by
     * @param sortOrder the sort order 
     */
    public TableModel(int pageNumber, int rowsAmountLimit, int recordsTotalAmount, 
            String sortedColumnIndex, String sortOrder) {
        
        this.recordsTotalAmount = recordsTotalAmount;
        
        // calculate the total pages for the query
        if (this.recordsTotalAmount > 0 && rowsAmountLimit > 0) {
            this.pagesAmount = ((Double) Math.ceil((double)this.recordsTotalAmount/rowsAmountLimit)).intValue();
        } 
        
        this.pageNumber = pageNumber;
        // if for some reasons the requested page is greater than the total 
        // set the requested page to total page
        if (this.pageNumber > this.pagesAmount) {
            this.pageNumber = this.pagesAmount;
        }
        
        // calculate the starting position of the rows
        this.fromIndex = (this.pageNumber - 1) * rowsAmountLimit;
        // if for some reasons start position is negative set it to 0
        // typical case is that the user type 0 for the requested page
        if (this.fromIndex < 0) {
            this.fromIndex = 0;
        }
        this.toIndex = this.fromIndex + rowsAmountLimit;

        if (this.toIndex > this.recordsTotalAmount) {
            this.toIndex = this.recordsTotalAmount;
        }
    }
    
    /**
     * Get the total records from the query.
     * 
     * @return the total records
     */
    public int getRecordsTotalAmount() {
        return recordsTotalAmount;
    }

    /**
     * Get start row index.
     * 
     * @return the index
     */
    public int getFromIndex() {
        return fromIndex;
    }

    /**
     * Get end row index.
     * 
     * @return the index
     */
    public int getToIndex() {
        return toIndex;
    }

    /**
     * Get the number of the requested page.
     * 
     * @return the number of page
     */
    public int getPageNumber() {
        return pageNumber;
    }
    
    /**
     * Get the total pages of the query.
     * 
     * @return the total pages
     */
    public int getPagesAmount() {
        return pagesAmount;
    }
    
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
