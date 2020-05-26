package org.cgfalcon.fluentexcel.entity;

/**
 * Author: falcon.chu
 * Date: 13-6-14
 * Time: 下午6:52
 */
public class Image {

    private int rowFrom = 1;
    private int colFrom = 1;
    private int rowTo = 1;
    private int colTo = 3;
    private String name;

    public int getRowFrom() {
        return rowFrom;
    }

    public void setRowFrom(int rowFrom) {
        this.rowFrom = rowFrom;
    }

    public int getColFrom() {
        return colFrom;
    }

    public void setColFrom(int colFrom) {
        this.colFrom = colFrom;
    }

    public int getRowTo() {
        return rowTo;
    }

    public void setRowTo(int rowTo) {
        this.rowTo = rowTo;
    }

    public int getColTo() {
        return colTo;
    }

    public void setColTo(int colTo) {
        this.colTo = colTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
