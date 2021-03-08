package org.supercall.domainModel.framework;

import java.util.List;

/**
 * Created by kira on 16/8/3.
 */
public class SimpleGridConfigModel {
    private String entityName;

    private List<GridColumnConfig> gridColumnConfigs;

    private List<TableFilterConfig> tableFilterConfigs;

    public SimpleGridConfigModel(String entityName, List<GridColumnConfig> gridColumnConfigs, List<TableFilterConfig> tableFilterConfigs) {
        this.entityName = entityName;
        this.gridColumnConfigs = gridColumnConfigs;
        this.tableFilterConfigs = tableFilterConfigs;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<GridColumnConfig> getGridColumnConfigs() {
        return gridColumnConfigs;
    }

    public void setGridColumnConfigs(List<GridColumnConfig> gridColumnConfigs) {
        this.gridColumnConfigs = gridColumnConfigs;
    }

    public List<TableFilterConfig> getTableFilterConfigs() {
        return tableFilterConfigs;
    }

    public void setTableFilterConfigs(List<TableFilterConfig> tableFilterConfigs) {
        this.tableFilterConfigs = tableFilterConfigs;
    }
}
