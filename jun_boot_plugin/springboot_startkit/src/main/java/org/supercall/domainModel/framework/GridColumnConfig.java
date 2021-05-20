package org.supercall.domainModel.framework;

/**
 * Created by kira on 16/8/3.
 */
public class GridColumnConfig {
    private String colunmName;

    public GridColumnConfig(String colunmName) {
        this.colunmName = colunmName;
    }

    public String getColunmName() {
        return colunmName;
    }

    public void setColunmName(String colunmName) {
        this.colunmName = colunmName;
    }
}
